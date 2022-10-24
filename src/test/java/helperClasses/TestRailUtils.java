package helperClasses;

import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static helperClasses.TestProperty.*;
import static helperClasses.UtilityMethods.*;


public class TestRailUtils {
    public static final int TEST_CASE_PASSED_STATUS = 1;
    public static final int TEST_CASE_FAILED_STATUS = 5;
    //log	private static Logger log = Logger.getLogger(TestRailUtils.class);

    public static void uploadToRails(String mappingSearchQuery, String status,
                                     String timeElapsed) throws APIException {
        //Ensure 'uploadToRails' not called if query too generic.
        if (mappingSearchQuery.equalsIgnoreCase("Pega-ViaNovo") ||
                mappingSearchQuery.equalsIgnoreCase("createCase")) {
        } else {
            // Get list of Test Case ID
            ArrayList<String> testCaseIdList = TestRailUtils.getTestCaseId(mappingSearchQuery);
            // String testCaseId = TestRailUtils.getTestCaseId(mappingSearchQuery,methodName);

            // Status Code for rails
            int statusCode = TestRailUtils.TEST_CASE_PASSED_STATUS;
            if (status.equals(TEST_FAILED)) {
                statusCode = TestRailUtils.TEST_CASE_FAILED_STATUS;
            }

            // Upload the result to rails
            for (String testCaseId : testCaseIdList){
                if (!testCaseId.equals("")) {
                    try {
                        TestRailUtils.addResultForTestCase(testCaseId, statusCode, timeElapsed);
                    } catch (IOException e) {
                        logError("Error uploading result to rails" + e);
                        e.printStackTrace();
                    }
                }
            }

            if (testCaseIdList.isEmpty()) {
                logError("Test case not mapped in TestRail: " + mappingSearchQuery);
            }
        }
    }


    private static Properties readInProperties() throws IOException {
        FileInputStream in = null;
        Properties prop = new Properties();
        try {
            in = new FileInputStream(new File(System.getProperty("user.dir")+
                    TEST_RUN_ID));
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return prop;
    }

    private static String inputStreamToString(InputStream is)
            throws IOException {
        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        while ((line = rd.readLine()) != null) {
            total.append(line);
        }

        // Return full string
        return total.toString();
    }



    public static void addResultForTestCase(String testCaseId, int status,
                                            String timeElapsed) throws IOException, APIException {
        TestRailAPIClient client = new TestRailAPIClient(TESTRAIL_URL);
        client.setUser(TestProperty.TESTRAIL_USERNAME);
        client.setPassword(TestProperty.TESTRAIL_PASSWORD);
        Map data = new HashMap();

        //Handling in case test elapsed time is equal to 0s.
        if (timeElapsed.equalsIgnoreCase("0s")) {
            timeElapsed = "1s";
        }

        if (!TEST_RUN_ID.isEmpty()) {
            data.put("status_id", status);
            data.put("elapsed", timeElapsed);
            //TODO Complete TestRail testdetails
            data.put("testdetails", "**PLACEHOLDER**");
            data.put("version", TEST_ENV);
            data.put("environment", TEST_ENV);
            data.put("comment", "Test Completed - Status updated from automated test result.");
            JSONObject c = (JSONObject) client.sendPost("add_result_for_case/" + TEST_RUN_ID + "/" + testCaseId + "", data);

            logStringIntoConsole("Mapped result for Case #" + testCaseId + " into TestRail successfully.");
        } else {
            logStringIntoConsole("TestRail Run ID EMPTY...");
        }
    }

    public static ArrayList<String> getTestCaseId(String searchQuery) {
        logStringIntoConsole("Search Query: " + searchQuery + " | (CASE INSENSITIVE)");


        NodeList matchNodes ;
        ArrayList<String> idValList = new  ArrayList<>();
        Document doc = null;
        try {
            doc = getTestCasesDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //modified by rahul.nair
        //A method can be selectively update a test case ID based up on the "testcasename" node in the xml
        //Changes were made such that a method can update multiple test cases if the same method is tagged
        //to multiple test case IDs in the xml.
        if (doc != null) {
            try {
                XPath xPath = XPathFactory.newInstance().newXPath();

                //Input via lower case to ensure casing doesn't impact mapping.
                searchQuery = searchQuery.toLowerCase();

                XPathExpression xPathExpression = xPath
                        .compile(".//testcases//testcase//title[   contains(     translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),     '" + searchQuery + "'   ) ]//preceding-sibling::id");
                matchNodes = (NodeList)(xPathExpression.evaluate(doc,XPathConstants.NODESET));


                for (int i=0; i < matchNodes.getLength(); i++){
                    idValList.add(matchNodes.item(i).getTextContent().trim());
                }

            } catch (XPathExpressionException e) {
            }
        }

        return idValList;
    }

    private static Document getTestCasesDocument()
            throws ParserConfigurationException, SAXException, IOException {
        String pathToFile = TESTRAIL_CASES;
        return readXMLFile(pathToFile);
    }


}