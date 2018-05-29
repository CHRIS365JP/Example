package example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertiesUtil {
    
    private static Properties props;
    
    private static String path;
    
    public PropertiesUtil(String path) {
        loadProps(path);
    }
    
//    static {
//        loadProps();
//    }
//    
//    synchronized static private void loadProps() {
//        props = new Properties();
//        InputStream loadIn = null;
//        try {
//            loadIn = PropertiesUtil.class.getClassLoader().getResourceAsStream("");
//            props.load(loadIn);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (loadIn != null) {
//                try {
//                    loadIn.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    
    synchronized private void loadProps(String path) {
        props = new Properties();
        InputStream loadIn = null;
        try {
            if(path.endsWith(".properties")) {
                loadIn = PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
            } else {
                loadIn = PropertiesUtil.class.getClassLoader().getResourceAsStream(path + ".properties");
            }
            props.load(loadIn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (loadIn != null) {
                try {
                    loadIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * メッセージのkeyから、メッセージ内容を取得する
     * 
     * @param messageId メッセージのID
     * @return メッセージ内容
     */
    public Map getMessage(String messageId) {
        String message = null;
        if (null == props) {
            loadProps(path);
        }
        props.elements();
        Map propertiesMap = new HashMap();
        for (Entry<Object, Object> e : props.entrySet()) {
            propertiesMap.put(e.getKey(),e.getValue());
        }
        return propertiesMap;
    }
    
    public Object getMessages(String messageId) {
        String[] message = null;
        if (null == props) {
            loadProps(path);
        }
//        message = (String[])props.get(messageId);
        return props.get(messageId);
    }

    /**
     * メッセージのkeyとデフォルト内容から、メッセージ内容を取得する
     * 
     * @param key メッセージのkey
     * @param defaultValue デフォルト内容
     * @return 内容
     */
    public String getMessage(String key, String defaultValue) {
        if (null == props) {
            loadProps(path);
        }
        return props.getProperty(key, defaultValue);
    }
}
