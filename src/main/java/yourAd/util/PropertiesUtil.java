package yourAd.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties load(String filePath){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filePath));
        } catch (IOException e) {
            System.out.println("failed load properties");
            e.printStackTrace();
        }
        return properties;
    }
}
