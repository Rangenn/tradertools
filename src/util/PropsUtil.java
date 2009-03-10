/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author е
 */
public class PropsUtil {

    private static Properties Props;

    static {
        try {
            Props = new Properties();
            Props.load(new FileInputStream("src/view/resources/language.properties"));
        } catch (IOException ex) {
            System.out.println(ex.toString());
            Props.clear();
            //throw new ExceptionInInitializerError(ex);
        }
    }

    public static Properties getProps(){
        return Props;
    }

    public static String getProperty(String key){
        return Props.getProperty(key);
    }

}
