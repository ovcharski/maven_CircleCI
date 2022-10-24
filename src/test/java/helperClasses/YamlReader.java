package helperClasses;

import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static helperClasses.UtilityMethods.doesStringContainSomeText;
import static helperClasses.UtilityMethods.logError;
import static helperClasses.UtilityMethods.logStringIntoConsole;


public class YamlReader {

    public static String yamlFilePath;

    public YamlReader(String fileName){
        yamlFilePath = System.getProperty("user.dir") + File.separator+"src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "YML" + File.separator + fileName;

        logStringIntoConsole("Yaml file path: "+ yamlFilePath);
        Reader doc = null;
        try{
            doc = new FileReader(yamlFilePath);
        }
        catch(FileNotFoundException e){
            logError("File not found exception " +e);
        }
    }

    public static String getValue(String token){
        Reader doc = null;
        try{
            doc = new FileReader(yamlFilePath);
        }
        catch(FileNotFoundException e){
            logError("File not found exception " +e);
        }


        Yaml yaml = new Yaml();
        @SuppressWarnings("unchecked")
        Map<String, Object> object = (Map<String, Object>) yaml.load(doc);
        //logStringIntoConsole("#Test Data#: " + getMapValue(object, token));
        return getMapValue(object, token);
    }

    public static String getMapValue(Map<String, Object> object, String token) {
        String[] st = token.split("\\.");
        String result = null;
        try {
            result = new String(parseMap(object, token).get(st[st.length - 1]).toString().getBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logError("Key: " + token + " | Value: " + result);
        }
        logStringIntoConsole("Key: " + token + " | Value: " + result);


        if (result.equalsIgnoreCase("TRUE") | result.equalsIgnoreCase("FALSE")) {
            logError("**NOTE**  True/False returned as value!  **NOTE** ");
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> parseMap(Map<String, Object> object,String token) {
        if (doesStringContainSomeText(token, ".")) {
            String[] st = token.split("\\.");
            object = parseMap((Map<String, Object>) object.get(st[0]),token.replace(st[0] + ".", ""));
        }
        return object;
    }
}