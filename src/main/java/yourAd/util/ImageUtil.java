package yourAd.util;

import lombok.Cleanup;

import java.io.*;
import java.util.Properties;

public class ImageUtil {

    private static final String FILE_PATH = "C:\\Users\\Erik\\IdeaProjects\\java-web\\YourAd\\src\\main\\resources\\img-config.properties";
    private static final Properties PROPERTIES = PropertiesUtil.load(FILE_PATH);
    private static final String [] IMAGE_FORMATS = {"image/jpeg","image/png"};


    public static boolean isValidFormat(String format){
        for (String imageFormat : IMAGE_FORMATS) {
            if(imageFormat.equals(format)){
                return true;
            }
        }
        return false;
    }

    public static void save(String parent,String imageName,byte[] bytes){
        try {
            File file = new File(PROPERTIES.getProperty("images.default.path"),parent);
            if(!file.exists()){
                if(!file.mkdirs()){
                    throw new RuntimeException("file failed create");
                }
            }
            @Cleanup FileOutputStream out = new FileOutputStream(new File(file,imageName));
            out.write(bytes);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void delete(String fileName){
        File file = new File(PROPERTIES.getProperty("images.default.path"),fileName);
        if(file.exists()){
            file.delete();
        }
    }

    public static boolean exists(String fileName){
        File file = new File(PROPERTIES.getProperty("images.default.path"),fileName);
        return file.exists();
    }

    public static byte[] getBytes(String fileName){
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[8000];
        int length;
        try {
            FileInputStream inputStream = new FileInputStream(new File(PROPERTIES.getProperty("images.default.path"),fileName));
            while ((length = inputStream.read(buff)) != -1){
                arrayOutputStream.write(buff,0,length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayOutputStream.toByteArray();
    }
}
