package com.jf.util.pplogin;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * @author tianshuhang
 * @ClassName: Encry
 * @Description: TODO(p1, p2, p3加密的类)
 * @date 2015年7月31日 下午2:37:56
 */
class Encry {

    public static final String HEXSTRING = "0123456789ABCDEF";

    public String encryp1(String v1, String v2) {
        String ret = "";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String jsFileName = System.getProperty("user.dir") + "/js/encry.js";
        FileReader reader;
        try {
            reader = new FileReader(jsFileName);
            engine.eval(reader);
            String jsstr = "function getm32strss(){" + " var keyValue = getm32str('" + v1 + "', '" + v2 + "'); " + " return keyValue ; " + "} ";
            engine.eval(jsstr);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                ret = (String) invoke.invokeFunction("getm32strss");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String encryp2(String v1, String v2) {
        String ret = "";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String jsFileName = System.getProperty("user.dir") + "/js/encry.js";
        FileReader reader;
        try {
            reader = new FileReader(jsFileName);
            engine.eval(reader);
            String jsstr = "function getm16strss(){" + " var keyValue = getm16str('" + v1 + "', '" + v2 + "'); " + " return keyValue ; " + "} ";
            engine.eval(jsstr);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                ret = (String) invoke.invokeFunction("getm16strss");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String encry(String v1, String v2, String v3) {
        String ret = "";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        FileReader reader;
        try {
            reader = new FileReader(System.getProperty("user.dir") + "/js/rsa.pack.js");
            engine.eval(reader);
            String jsstr = "function Encrypt(password){" + "  " + " setMaxDigits(131);" + " var encrypted = encryptedString(new RSAKeyPair('" + v2 + "','','"
                    + v3 + "'),password); " + " return encrypted ; " + "} ";
            engine.eval(jsstr);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                ret = (String) invoke.invokeFunction("Encrypt", v1);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

}
