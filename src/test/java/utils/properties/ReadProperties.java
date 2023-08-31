package utils.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {


    public static String readProperty(String key) {
        Properties prop = new Properties();
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\configuration.properties";
        try (FileInputStream fis = new FileInputStream(filePath)) {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }

}
