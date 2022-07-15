package utils.common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {
    private JSONObject jsonObject;

    public JsonReader(String fileName) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(fileName));
            jsonObject = (JSONObject) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject get(String key) {
        return (JSONObject) jsonObject.get(key);
    }
}