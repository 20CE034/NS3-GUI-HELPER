/**
 * Program name: ApplicationSettingsHelper
 * Program date: 05-01-2024
 * Program owner: henil
 * Contributor:
 * Last Modified: 05-01-2024
 * <p>
 * Purpose: This class will be used for managing the settings for application environment...
 */
package Helpers;

import FileHandler.FileReaderWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApplicationSettingsHelper {
    // list of settings...
    public static String OUTPUT_PATH = "outputPath";
    public static String FILE_NAME = "fileName";
    public static String DEFAULT_LINKS = "defaultLinks";

    public static JSONObject UNIVERSAL_SETTINGS = new JSONObject();
    public static String OutputPath = System.getProperty("user.dir");

    // static methods...
    public static void setUpSettings() {
        setUpSettings(System.getProperty("user.dir"));
    }

    public static void setUpSettings(String OutputPath) {
        UNIVERSAL_SETTINGS.put(FILE_NAME, "output");
        UNIVERSAL_SETTINGS.put(OUTPUT_PATH, OutputPath);
        String d = FileReaderWriter.readIfEmptyUsingPath(UNIVERSAL_SETTINGS.toString(), OutputPath+"\\file.txt");
        JSONObject objJ = new JSONObject(d);
        for (String key : objJ.keySet()) {
            if (UNIVERSAL_SETTINGS.has(key)) {
                UNIVERSAL_SETTINGS.remove(key);
                UNIVERSAL_SETTINGS.put(key, objJ.get(key));
            } else {
                UNIVERSAL_SETTINGS.put(key, objJ.get(key));
            }
        }
    }

    public static void saveSettings()
    {
        FileReaderWriter.writeUsingPath(UNIVERSAL_SETTINGS.toString(), OutputPath+"\\file.txt");
        DebuggingHelper.Debugln("Updated to file!! Settings has been saved in file!!");
    }

    public static boolean hasDefaultLinks() {
        return UNIVERSAL_SETTINGS.has(DEFAULT_LINKS);
    }

    public static ArrayList<LinkHelper> getDefaultLinks() {
        ArrayList<LinkHelper> links = new ArrayList<>();
        if (hasDefaultLinks()) {
            for (Object data : ((JSONArray)UNIVERSAL_SETTINGS.get(DEFAULT_LINKS))) {
                String[] params = data.toString().split("\\|");
                for (String str : params) {
                    DebuggingHelper.Debugln("Str : "+str);
                }
                links.add(new LinkHelper(0, params[0], params[1], params[2], params[3]));
            }
            DebuggingHelper.Debugln("Yes! Universal Settings has Default Links!");
        } else {
            DebuggingHelper.Debugln("No! Universal Settings has no default links!");
        }

        DebuggingHelper.Debugln("Returning the links as array list.");
        return links;
    }
}
