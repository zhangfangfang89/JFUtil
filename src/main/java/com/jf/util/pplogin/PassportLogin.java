package com.jf.util.pplogin;

import com.jf.util.common.HttpRequestBase;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName: TestLogin
 * @Description: TODO(58同城自动登录)
 * @author tianshuhang
 * @timesign 2015年7月30日 下午4:21:10
 * <p>
 * Form 表单有一下几个动态参数需要自己动态生成
 * p1：第一次加密，是通过lib/wuba.js中的getm32str($("#password").val(), timesign +
 * "")方法加密的 p2：第二次加密，是通过lib/wuba.js中的getm16str($("#password").val(),
 * timesign + "")方法加密的
 * p3：第三次加密，是通过lib/entry.js中的encryptString(timesign +
 * encodeURIComponent($("#password").val()), "010001",
 * "公钥")加密的，公钥可以用过在html中查找获取 cd:
 * 动态变化的参数，需要通过Jsoup动态从html标签<input>的value提取值 ptk:
 * 动态变化的参数，需要通过Jsoup动态从html标签<input>的value提取值
 */

/**
 * 问题解决办法，当登录用户发布不能获取到PPU信息的时候，需要去查看此用户是否为高危或者锁定等非法用户
 */

public class PassportLogin {

    /**
     * @param @param  username 用户名
     * @param @param  password 密码
     * @param @return
     * @param @throws Exception 设定文件
     * @return String 返回类型
     * @throws
     * @Title: login
     * @Description: TODO(自动登录的方法)
     */
    public static String login(String username, String password)
            throws Exception {

        // result为返回PPU的值
        String result = null;
        String returnStr = null;
        try {
            // 读取JS文件
            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(System.getProperty("user.dir") + "//js//wuba.js"))));

            // 调用js
            ScriptEngineManager scriptManager = new ScriptEngineManager();
            ScriptEngine js = scriptManager.getEngineByExtension("js");
            // 执行JS
            js.eval(buf);
            // 获取时间戳
            long timesign = new Date().getTime();
            System.out.println("时间戳timesign:" + timesign);

            // Password是加密p3的类
            Encry p = new Encry();
            // 这里的公钥可以从html取下来，是固定的
            String p3 = p.encry(timesign + URLEncoder.encode(password), "010001",
                    "008baf14121377fc76eaf7794b8a8af17085628c3590df47e6534574efcfd81ef8635fcdc67d141c15f51649a89533df0db839331e30b8f8e4440ebf7ccbcc494f4ba18e9f492534b8aafc1b1057429ac851d3d9eb66e86fce1b04527c7b95a2431b07ea277cde2365876e2733325df04389a9d891c5d36b7bc752140db74cb69f");

            Map<String, String> parameters = new HashMap<String, String>();
            Map<String, String> headers = new HashMap<String, String>();

            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
            headers.put("Referer", "http://passport.58.com/login?path=https://my.58.com/index");
            headers.put("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
//			headers.put("Cookie", "id58=c5/nn1wzLTU1FddXQuS8Ag==;xxzl_deviceid=NGYbXzjBKiH5gA4dLc1wFmQ3gI5T4DcK1WOSHyfghH9ZiiOLWenkbZiDF3HC9%2FpC;xxzl_smartid=1489b092b978d2a77a1b26e7ea1bef57");

            // 实例化表单参数
            parameters.put("isweak", "0");
            parameters.put("path", "http://my.58.com/");
            parameters.put("timesign", String.valueOf(timesign));
            parameters.put("username", username);
            parameters.put("password", p3);
//			parameters.put("source", "pc-login");
            parameters.put("source", "passport");
            parameters.put("isremember", "false");
            parameters.put("callback", "successFun");
            parameters.put("yzmstate", "");
//			parameters.put("fingerprint", "E6D8D34F98D5D9E47513B35B5C2C4DAA942F7DE13D4037FE_011");
            parameters.put("fingerprint", "A65596227BC820F5F685737F7FC0D4F1092B0D6D806749E7_011");
            parameters.put("finger2", "zh-CN|24|1|4|1366_768|1366_728|-480|1|1|1|undefined|1|unknown|Win32|unknown|3|false|false|false|false|false|0_false_false|d41d8cd98f00b204e9800998ecf8427e|b20e9276e5b8fff7cfacdad587b5ea41");
//            HttpRequestBase.doPost("http://passport.58.com/login/pc/dologi",parameters, headers);
            NewHttpRequestProxy hrp = new NewHttpRequestProxy();

            String[] temp = null;
            try {
                returnStr = hrp.doRequest("http://passport.58.com/login/pc/dologin", parameters, headers, "UTF-8");
                temp = returnStr.split("\\*");

                for (int i = 0; i < temp.length; i++) {
                    if (temp[i].contains("PPU")) {
                        result = temp[i];
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, String> setUserHeaderInfo(String userName, String passWord) throws Exception {
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Referer", "https://mfangxin.58.com/m");
        headerMap.put("cookie", login(userName, passWord));
        System.out.println(headerMap.get("cookie"));
        return headerMap;
    }

    public static void main(String[] args) {
        try {
//			PcLogin.login("testfwh25","aaa111");
//			String s = PassportLogin.login("lanroness9","qweszxc1@3");

            String cookie = PassportLogin.login("19959539667", "fupo123");
            System.out.println(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
