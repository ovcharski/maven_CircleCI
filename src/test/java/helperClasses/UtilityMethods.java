package helperClasses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * <h1>This class has all the utility methods which are used by the Page Classes
 * </h1>
 * <p>

 */
public class UtilityMethods {
    private static final String TASKLIST = "tasklist";
    private static final String KILL = "taskkill /F /IM ";


    private static Logger logger = LogManager.getLogger();
    public static void logStringIntoConsole(String textToLog){
        logger.info(textToLog);
    }
    public static void logError(String textToLog){
        logStringIntoConsole("  **  ERROR  **");
        logger.error(textToLog + " | ** ERROR **");
        logStringIntoConsole("  **  ERROR  **");
    }


    /**
     * Random String Generator
     * - Oleg Andreyev (7.20.16)
     */
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static final String numeric = "0123456789";
    static final String numericExcludingZero = "123456789";

    static SecureRandom rnd = new SecureRandom();

    public static String returnRandomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static String returnRandomString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam purus risus, gravida aliquet cursus " +
                "eu, ullamcorper eget nulla. Curabitur et augue in tortor accumsan euismod. Etiam sed tristique diam, " +
                "non congue nunc.");
        return sb.toString();
    }

    public static String randomNumericString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(numeric.charAt(rnd.nextInt(numeric.length())));
        return sb.toString();
    }

    public static String randomNumericString(String len) {
        int length = Integer.parseInt(len);

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < length; i++)
            sb.append(numeric.charAt(rnd.nextInt(numeric.length())));
        return sb.toString();
    }

    public static String randomNumericStringExcludeZero(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(numericExcludingZero.charAt(rnd.nextInt(numericExcludingZero.length())));
        logStringIntoConsole("Random Numeric String: " + sb.toString());
        return sb.toString();
    }

    public static String randomNumericStringExcludeZero(String len) {
        int length = Integer.parseInt(len);

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < length; i++)
            sb.append(numericExcludingZero.charAt(rnd.nextInt(numericExcludingZero.length())));
        logStringIntoConsole("Random Numeric String: " + sb.toString());
        return sb.toString();
    }

    /**
     * This method returns the label text of the Selected option
     *
     * @param element
     *            accessed WebElement
     * @return Selected Element Identifier
     */
    public static String getSelectText(WebElement element) {
        String selTxt = null;

        List<WebElement> optionsSelect = element.findElements(By
                .tagName("option"));
        for (int count = 0; count <= (optionsSelect.size() - 1); count++) {
            WebElement option = optionsSelect.get(count);
            if (option.isSelected()) {
                selTxt = option.getText();
            }
        }
        return selTxt;
    }

    /**
     * This method returns the list of options available in the dropdown
     *
     * @param element
     *            accessed WebElement
     * @return Array with list of options available in the dropdown
     */
    public static ArrayList<String> getSelectOptions(WebElement element) {
        List<String> selOptValue = new ArrayList<String>();

        List<WebElement> optionsSelect = element.findElements(By
                .tagName("option"));
        logStringIntoConsole("The size is - " + optionsSelect.size());
        for (int count = 0; count <= (optionsSelect.size() - 1); count++) {
            WebElement option = optionsSelect.get(count);
            selOptValue.add(option.getText());
            logStringIntoConsole("The option no is--" + count
                    + ". The option Text is ****" + option.getText());
        }
        return (ArrayList<String>) selOptValue;
    }

    /**
     * This method compares the elements of two ArrayLists
     *
     * @param listString1
     *            List 1
     * @param listString2
     *            List 2
     * @return Boolean with the result of comparison of two lists
     */
    public static boolean compareTwoList(ArrayList<String> listString1,
                                         ArrayList<String> listString2) {

        logStringIntoConsole("Comparing the two Arraylist " + listString1 + " and "
                + listString2 + " for equality...");
        boolean isVerify = false;
        if (listString1.size() == listString2.size()) {

            verifyPresenceOfItems: for (int countList1 = 0; countList1 <= (listString1
                    .size() - 1); countList1++) {
                isVerify = false;
                for (int countList2 = 0; countList2 <= (listString2.size() - 1); countList2++) {
                    if (listString1.get(countList1).equals(
                            listString2.get(countList2))) {
                        isVerify = true;
                        logStringIntoConsole("The item is found in the both Lists! The current item on the page is--"
                                + listString1.get(countList1)
                                + " and the item from the list is  --"
                                + listString2.get(countList2));
                    }
                }
                if (!isVerify) {
                    logStringIntoConsole("One of the Items is not found in the other list...");
                    break verifyPresenceOfItems;
                }

            }
        }
        return isVerify;
    }

    /**
     * This method converts an ArrayList of Strings to Array of Strings
     *
     * @param strList
     *            ArrayList of Strings
     * @return Array of Strings
     */
    public static String[] convertArrListToString(
            ArrayList<String> strList) {
        logStringIntoConsole("Converting ArrayList to array");
        return (String[]) strList.toArray();
    }

    /**
     * This method converts an Array of Strings to ArrayList of Strings
     *
     * @param strArr
     *            Array of Strings
     * @return ArrayList of Strings
     */
    public static ArrayList<String> convertStringArrToArrList(String[] strArr) {
        ArrayList<String> strArrList = new ArrayList<String>();
        for (int count = 0; count <= (strArr.length - 1); count++) {
            strArrList.add(strArr[count]);
        }
        return strArrList;
    }


    public static boolean isInteger(String testString) {
        try {
            Integer.parseInt(testString);
        } catch(NumberFormatException e) {
            logError("The String-- " + testString + " cannot be converted into an integer");
            return false;
        }
        return true;
    }

    public static boolean isProcessRunning(String serviceName) throws Exception {

        Process p = Runtime.getRuntime().exec(TASKLIST);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {

            System.out.println(line);
            if (line.contains(serviceName)) {
                return true;
            }
        }

        return false;

    }

    public static void killProcess(String serviceName) throws Exception {

        Runtime.getRuntime().exec(KILL + serviceName);

    }

    /**
     +	 * Summary - This method verifies the HTTP Status Code of the provided URL parameter.
     +	 * 				Will return error if 404 or 500 errors returned.
     +	 * Author - Oleg Andreyev
     +	 *
     +	 * @param urlString
     +     * @return
     +     */
    public static boolean getResponseCode(String urlString) {
        boolean isValid = false;
        try {
            URL u = new URL(urlString);
            HttpURLConnection h = (HttpURLConnection) u.openConnection();
            h.setRequestMethod("GET");
            h.connect();
            System.out.println("HTTP Response Code - " + h.getResponseCode() + " ::: " + urlString);
            if (h.getResponseCode() != 404) {
                isValid = true;
            }
            if (h.getResponseCode() != 500) {
                isValid = true;
            }
        } catch (Exception e) {
            Assert.fail("HTTP Error detected on the following URL: " + urlString);
        }
        return isValid;
    }

    public static ArrayList<String> splitPipeSepratedString(String pipeSepratedSting){

        ArrayList<String> alist = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(pipeSepratedSting, "|");
        while(st.hasMoreTokens()){
            alist.add(st.nextToken());
        }
        return alist;

    }

    public static boolean compareMaps(HashMap<String, String> map1, Map<String, String> map2) {
        boolean flag = false;
        for (String key1 : map1.keySet()) {
            if (map2.get(key1)!=null){
                if (map1.get(key1).trim().contains((map2.get(key1).trim()))){
                    flag=true;
                } else {
                    logStringIntoConsole("The key values NOT matches for - " + key1);
                    flag=false;
                }
            } else {
                logStringIntoConsole("The Key - " + key1 + " is NOT present in map2");
                flag=false;
            }
        }
        return flag;
    }

    /**
     * To be used when you want to have string comparisons done more easily.
     *
     * Example: Does longerString 'Unsure of Date' contain the shorterString 'Unsure'.  Yes.
     *
     * @param shorterString
     * @param longerString
     * @return
     */
    public static boolean doesStringContainSomeText(String longerString, String shorterString) {
        boolean result = false;

        longerString = "" + longerString.trim().toUpperCase();
        //logStringIntoConsole("Long String: " + longerString);
        shorterString = "" + shorterString.trim().toUpperCase();
        //logStringIntoConsole("Short String: " + shorterString);

        if (longerString.contains(shorterString)){
            logStringIntoConsole("Yes, '" + longerString + "' contains '" + shorterString + "'....");
            result = true;
        } else {
            logStringIntoConsole("No, STRING MISMATCH | '" + longerString + "' doesn't contain '" + shorterString +
                    "'");
        }
        return result;
    }

    public static boolean doesStringContainSomeTextNoLogging(String longerString, String shorterString) {
        boolean result = false;

        longerString = "" + longerString.trim().toUpperCase();
        shorterString = "" + shorterString.trim().toUpperCase();


        if (longerString.contains(shorterString)){
            result = true;
        } else {
        }
        return result;
    }

    /**
     * Example: System.getProperty("user.dir") + File.separator+"src" + File.separator + "test"
     *                 + File.separator + "resources" + File.separator + "savedData" + File.separator + terminateFile.txt
     * @param filePath
     * @return
     */
    public static String returnContentFromTextFileAddConcatenatedPipe(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));

        String fullValue = new String(encoded);
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                //sb.append("\n");
                line = br.readLine();
            }

        } finally {
            br.close();
        }

        String completeValue = fullValue + "|";
        logStringIntoConsole("Returning Content From File With Concatenated Pipe");
        logStringIntoConsole("Value: " + completeValue);
        logStringIntoConsole("Method: returnContentFromTextFileAddConcatenatedPipe");
        return completeValue;
    }

    public static String getTextFromFile(String fileNameWithoutExtension) {
        try {
            String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                    + File.separator + "resources" + File.separator + "savedData" + File.separator + fileNameWithoutExtension + ".txt";

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                }
                String[] text = sb.toString().split("\\s+");
                logStringIntoConsole("***" + fileNameWithoutExtension + ": " + text[0].trim());
                return text[0].trim();
            } finally {
                br.close();
            }
        } catch (Exception e) {
            logError(e.toString());
            throw new AssertionError(e.toString());
        }
    }

    public static void saveTextToFile(String fileName, String desiredContentsToSave) {
        File fold=new File(System.getProperty("user.dir") + File.separator+"src" +File.separator+"test"
                + File.separator + "resources" + File.separator + "savedData" + File.separator + fileName);
        fold.delete();
        File fnew=new File(System.getProperty("user.dir") + File.separator+"src" +File.separator+"test"
                + File.separator + "resources" + File.separator + "savedData" + File.separator + fileName);

        logStringIntoConsole("#### SAVED DATA: " + desiredContentsToSave + " ####");

        try {
            FileWriter f2 = new FileWriter(fnew, false);
            f2.write(desiredContentsToSave);
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void softAssert(boolean expected, String comment) {
        SoftAssert soft = new SoftAssert();

        comment = comment + " | ";

        soft.assertEquals(expected, true, comment + " | ");
        //soft.assertAll();
        soft = null;

    }

    public static void softAssert(boolean expected) {
        SoftAssert soft = new SoftAssert();


        soft.assertEquals(expected,true);
        //soft.assertAll();
        soft = null;

    }

    public static void saveTextInFile(String fileNameWithExtension, String text) {
        File fold=new File(System.getProperty("user.dir") + File.separator+"src" +File.separator+"test"
                + File.separator + "resources" + File.separator + "savedData" + File.separator + fileNameWithExtension);
        fold.delete();
        File fnew=new File(System.getProperty("user.dir") + File.separator+"src" +File.separator+"test"
                + File.separator + "resources" + File.separator + "savedData" + File.separator + fileNameWithExtension);

        logStringIntoConsole("#### SAVED DATA: " + text + " ####");

        try {
            FileWriter f2 = new FileWriter(fnew, false);f2.write(text);
            f2.close();
        }
        catch (IOException e) { e.printStackTrace();
        }
    }

    /**
     * Inclusive random integer return.
     *
     * @param min
     * @param max
     * @return
     */
    public static int returnRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public Integer returnRandomNumber(Integer maximum) {
        Random r = new Random();
        int value = r.nextInt(maximum);
        value += 1;

        return value;
    }

    /**
     * Returns test names from a provided XML Test Suite.
     *
     * **NOTE** Ensure that DOCTYPE is removed prior to parsing.
     *
     *
     * @param suiteName
     */
    public static void returnTestCaseNameFromXMLTestSuite(String suiteName) {
		/*
		1. Extract test names.
		2. Substring to ensure clean
		3. Save them.

		XPath for test case == .//test/@name
		 */


        String suitePath = "\\src\\test\\resources\\_testSuites\\" + suiteName;
        String testCasePath = "\\src\\test\\resources\\savedData\\testCases.txt";
        NodeList matchNodes;
        Document doc = null;
        try {
            doc = getDocument(suitePath);

            if (doc != null) {
                logStringIntoConsole("Processing document...");

                XPath xPath = XPathFactory.newInstance().newXPath();
                XPathExpression xPathExpression = xPath.compile("//test/@name");
                matchNodes = (NodeList) (xPathExpression.evaluate(doc, XPathConstants.NODESET));
                logStringIntoConsole("Node Length = " + matchNodes.getLength());

                System.out.println();
                for (int i = 0; i < matchNodes.getLength(); i++) {
                    String test = matchNodes.item(i).getNodeValue();
                    System.out.println(test);
                }
            }
        } catch(Exception e){
        }
    }


    public static Document readXMLFile(String xml)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

        // InputSource is = new InputSource(new StringReader(xml));
        Document doc = docBuilder.parse(new File(System.getProperty("user.dir")+xml));

        // normalize text representation
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static Document getDocument(String path)
            throws ParserConfigurationException, SAXException, IOException {
        return readXMLFile(path);
    }


    private static Hashtable<String, String> table = null;


    public static boolean returnRandomBoolean() {
        if ((Math.random() < 0.5)){
            logStringIntoConsole("Random Boolean = TRUE");
            return true;
        } else {
            logStringIntoConsole("Random Boolean = FALSE");
            return false;
        }
    }

    public <T> T readYAML(String fileName, Class<T> t) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\YML\\" + fileName;
        try {
            return mapper.readValue(new File(filePath), t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeScreenshotBase64(String imgPath) {
        String base64Img = "";

        File inpFile = new File(imgPath);

        try(FileInputStream imgInFile = new FileInputStream(inpFile)) {
            byte[] imgData = new byte[(int) inpFile.length()];
            imgInFile.read(imgData);
            base64Img = Base64.getEncoder().encodeToString(imgData);

        } catch (FileNotFoundException e) {
            logError("The File is not found, in the embedScreenShotBase64 method of the ..");
        } catch (IOException ioe) {
            logError("The file is not readable, screenshot will not be available" + ioe );
        }

        logStringIntoConsole("Encode Screenshot: " + imgPath);
        return base64Img;
    }

    /**
     * Windows
     */
    public static void killChromeDriver() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        // Windows
        System.out.println();
        processBuilder.command("cmd.exe", "/c", "taskkill /F /IM chromedriver.exe /T");

        try {
            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String addDate(final String currTestName) {
        Date myDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yy-HH.mm.ss");
        return currTestName + "-" + sdf.format(myDate);
    }

    public static String addDate() {
        Date myDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yy-HH.mm.ss");
        return sdf.format(myDate);
    }
}
