/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fril;

import static fril.FrmLogin.LOGIN_FAIL;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author CuongPhan
 */
public class Utility {

    public static CookieStore gCookieStore = new CookieManager().getCookieStore();
    public static String gCookieID;
    private static String authenticity_token_static;
    private static String cookie_static;
    public static String cookieID; //used in getTokenToDeleteItem(), getTokenToUploadComment() 
    public static String token; //used in getTokenToDeleteItem(), getTokenToUploadComment()
    public static String comment_token; //used in getTokenToUploadComment()  
    public static int g_refreshPeriod = 60000; //60 second to refresh the item list
    
    static TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                //No need to implement.
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                //No need to implement.
            }
        }
    };

    // Install the all-trusting trust manager
    public static String getToken(String formUrl) throws MalformedURLException, IOException {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            System.out.println(e);
        }

        Utility.gCookieID = null;
        System.setProperty("proxyHost", "127.0.0.1");
        System.setProperty("proxyPort", "8888");
        String authenticityToken = "";
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;
        String resp;
        //http request
        URL url = new URL(formUrl);
        HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
        req.setRequestMethod("GET");
        req.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        req.setRequestProperty("Cookie", createCookie(Utility.gCookieID));

        //reads http response
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        resp = stringBuilder.toString();
        List<String> cookies = req.getHeaderFields().get("Set-Cookie"); //gets cookie

        // adds cookies to gCookieStore
        AddCookieStore(cookies);

        //gets authencitiToken
        int nIdxStart = resp.indexOf("csrf-token");
        int nIdxEnd = resp.indexOf("/>", nIdxStart);
        String strTmp = resp.substring(nIdxStart, nIdxEnd);
        String[] str = strTmp.split("\"");
        if (str.length >= 3) {
            authenticityToken = str[2];
        }
        return authenticityToken;
    }

     private static void AddCookieStore(List<String> cookies) {
        String[] cookieSplit;
        String cookieName;
        String cookieValue;
        String[] cookie;
        for (String listCookie : cookies) {
            cookieSplit = listCookie.split(";"); //splits cookie by ; (example: asd=afsdf;domain=...)
            cookie = cookieSplit[0].split("="); //example: splits asd=afsdf by =
            cookieName = cookie[0];
            cookieValue = cookie[1];
            HttpCookie httpCookie = new HttpCookie(cookieName, cookieValue);
            gCookieStore.add(null, httpCookie);
        }
    }
     
    public static void getTokenToDeleteItem(String formUrl) throws MalformedURLException, IOException{
        cookieID = "";
        token = "";
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;
        String resp;
        //http request
        URL url = new URL(formUrl);
        HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
        req.setRequestMethod("GET");
        req.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        req.setRequestProperty("Cookie", createCookie(Utility.gCookieID));
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        resp = stringBuilder.toString();
        String cookie = req.getHeaderField("Set-Cookie");
        String[] cookieSplit = cookie.split(";"); //splits cookie by ; (example: asd=afsdf;domain=...)
        String[] cookies = cookieSplit[0].split("="); //example: splits asd=afsdf by =
        cookieID = cookies[1]; //get cookie value
        
        int nIdxStart = resp.indexOf("csrf-token");
        int nIdxEnd = resp.indexOf("/>", nIdxStart);
        String strTmp = resp.substring(nIdxStart, nIdxEnd - nIdxStart - 1);
        String[] tmp = strTmp.split("\\\"");
        if(tmp.length >= 3){
            token = tmp[2]; //get token
        }
    }
   
    public static void getTokenToUploadComment(String formUrl) throws MalformedURLException, IOException{
        cookieID = "";
        token = "";
        comment_token = "";
        
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;
        String resp;
        //http request
        URL url = new URL(formUrl);
        HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
        req.setRequestMethod("GET");
        req.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        req.setRequestProperty("Cookie", createCookie(Utility.gCookieID));
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        resp = stringBuilder.toString();
        String cookie = req.getHeaderField("Set-Cookie");
        String[] cookieSplit = cookie.split(";"); //splits cookie by ; (example: asd=afsdf;domain=...)
        String[] cookies = cookieSplit[0].split("="); //example: splits asd=afsdf by =
        cookieID = cookies[1]; //get cookie value
        
        token = extractAttribute(resp, 0, "\"authenticity_token\" value=\"", "\" />");
        //get item_comment_authenticity_token
        comment_token = extractAttribute(resp, 0, "id=\"item_comment_authenticity_token\" value=\"", "\" />");
    }
    
    public static String createCookie(String cookieID) {
        HttpCookie httpCookie = new HttpCookie("_fril_user_session", cookieID);
        //httpCookie.setDomain("fril.jp");
        return httpCookie.toString();
    }

    public static boolean logIn(String username, String password) throws MalformedURLException, IOException {
        boolean bResult = false;
        String formUrl = "https://fril.jp/users/sign_in";
        getCookieAndAuthenticitytoken("https://fril.jp/");
        String formParams = "utf8=" + URLEncoder.encode("✓", "UTF-8") + "&authenticity_token=" + URLEncoder.encode(authenticity_token_static, "UTF-8") + "&user" + URLEncoder.encode("[", "UTF-8") + "email" + URLEncoder.encode("]", "UTF-8") + "=" + username + "&user" + URLEncoder.encode("[", "UTF-8") + "password" + URLEncoder.encode("]", "UTF-8") + "=" + password + "&commit=" + URLEncoder.encode("ログイン", "UTF-8");
        URL url = new URL(formUrl);
        HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
        req.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        req.setRequestProperty("Cookie", cookie_static);
        //req.setRequestProperty("Cookie", "_fril_user_session=aG01M09yU09iRGNYb2RFSmZZNnNRME5NTDdWb3Vib3oxSmJOdTlneFZqanplREplR0p6amFqaGxobFlIdU9udU9jdjRqME94dnhOY3lHcmE1Z0R1RUNBNGdsODQ1VEdyK2xhcEdnQ21uTkdlRzE2MFl4d1FheUFML3AyZ1gvSVBWUkcyOUd6eER3RjF5aUFKQktvT2owbGp1T2VDZ1VEdVVKV1kwckRVVnVza3RpbFc2QmJQeTR6NUZpT2JYZWZiVlZwZ2JmeStkUGdYRjZEQmdFS0dBblNveVhpWXcwdlhlNUZRd1hnTE11SlErZllRSGxFTkFrb2xzbkFiK2pLSlJkWkVCemJIdGp3ejlJdyt5ZDBlOGtYTzlIM2VnSEt2Vm05eFFsNDNDSVR4RU1UZWN1ZGx4ZE1hM1BINm1FR1NmTTA5eGFzU2JCZ0hOWE9RR2NHMXpIU2t4OXBUUGFFZEpkcjA4RCs0RnpzbkJIdkh6aEN2U1FWb0RiZFc0YVl0LS12Wks1UmNJdVRHblpsQXg2SWlKUzFBPT0%3D--e1957d2401b1df72d1e239c2eb07e200e8726e08");
        req.setInstanceFollowRedirects(false);
        req.setRequestMethod("POST");
        req.setRequestProperty("Referer", "https://fril.jp/users/sign_in");

        byte[] bytes = formParams.getBytes(StandardCharsets.US_ASCII);
        req.setRequestProperty("Content-Length", String.valueOf(bytes.length));
        //Send POST request
        req.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(req.getOutputStream())) {
            wr.writeBytes(formParams);
            wr.flush();
            wr.close();
        }

        List<String> cookies = req.getHeaderFields().get("Set-Cookie"); //get cookie
        //get cookie from response
        String[] cookieSplit;
        String[] cookieList;
        if (null != cookies) {
            for (String listCookie : cookies) {
                cookieList = listCookie.split(";"); //split cookie by ; (example: asd=afsdf;domain=...)
                cookieSplit = cookieList[0].split("="); //example: split asd=afsdf by =
                if ("_fril_user_session".equals(cookieSplit[0])) {
                    cookie_static = cookieSplit[1].replaceAll("\"", "");                
                }
            }
        }
        if (302 != req.getResponseCode()) { //login false
            JOptionPane.showMessageDialog(FrmLogin.jButton1, LOGIN_FAIL, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (FrmLogin.CheckBox.isSelected()) {
                Settings.rememberme = true;
                Settings.cookie = cookie_static;
            }
            gCookieID = cookie_static;
            
//            JFrame topFrame = (JFrame) frmLogin.getRootPane().getParent();
//            topFrame.dispose();
            
            ListItems listItems = new ListItems();
            listItems.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            listItems.pack();
            listItems.setVisible(true); 
            
            bResult = true;
        }
        //gCookie = cookie; cookie to addaccount
        getProducts("https://fril.jp/ajax/item/selling");
        return bResult;
    }
    
//    public static String getCsrkToken(String formUrl) throws MalformedURLException, IOException{
//        URL url = new URL(formUrl);
//        HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
//        req.setRequestMethod("GET");
//        req.setRequestProperty("Content-Type", "text/javascript; charset=utf-8");
//        req.setRequestProperty("Cookie", gCookieID);
//        
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(req.getInputStream()));
//        String inputLine;
//        StringBuilder stringBuilder = new StringBuilder();
//        String resp;
//        while ((inputLine = bufferedReader.readLine()) != null) {
//            stringBuilder.append(inputLine);
//        }
//        resp = stringBuilder.toString();
//        
//    }
    public static List<ItemShortInfo> getProducts(String formUrl) throws MalformedURLException, IOException {
        List<ItemShortInfo> listItem = new ArrayList<>();
        URL url = new URL(formUrl);
        HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
        req.setRequestMethod("GET");
        req.setRequestProperty("Content-Type", "text/javascript; charset=utf-8");
        req.setRequestProperty("Accept", "*/*");
        req.setRequestProperty("Cookie", gCookieID);
        req.setRequestProperty("X-Requested-With", "XMLHttpRequest");        
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        String resp;
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        resp = stringBuilder.toString();
        
        String[] tmp = resp.split("\'");
        for (String str : tmp) {
            if (str.startsWith("<div class")) {
                String strHtml = str.replace("\\", "");
                int idx = -1;
                while ((idx = strHtml.indexOf("class=\"media\"", idx + 1)) >= 0) {
                    String strHref = Utility.extractAttribute(strHtml, idx, "href=\"", "\"");
                    String strImgLink = Utility.extractAttribute(strHtml, idx, "src=\"", "\"");
                    String strMediaHeading = Utility.extractAttribute(strHtml, idx, "class=\"media-heading\">", "</h4>");
                    String strPattern = "";
                    if (strHtml.contains("class=\"waiting\">")) {
                        strPattern = "class=\"waiting\">";
                    } else if (strHtml.contains("class=\"has_todo\">")) {
                        strPattern = "class=\"has_todo\">";
                    }
                    String strWaiting = Utility.extractAttribute(strHtml, idx, strPattern, "</span>");
                    listItem.add(new ItemShortInfo(strHref, strImgLink, strMediaHeading, strWaiting));
                }
            }
        }
        return listItem;
    }
    
    public static String extractAttribute(String strHtml, int idx, String strStart, String strStop) {
        String strHref;
        try {
            int idxStart = strHtml.indexOf(strStart, idx) + strStart.length();
            int idxStop = strHtml.indexOf(strStop, idxStart);
            strHref = strHtml.substring(idxStart, idxStop);
        } catch (Exception e) {
            return "";
        }
        return strHref;
    }

    private static void getCookieAndAuthenticitytoken(String formUrl) throws MalformedURLException, IOException {
        //String authenticity_token = "";
        URL url = new URL(formUrl);
        HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
        req.setRequestMethod("GET");
        req.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        req.setRequestProperty("Cookie", Utility.gCookieID);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        String resp;
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        resp = stringBuilder.toString();
        int nIdxStart = resp.indexOf("csrf-token");
        int nIdxEnd = resp.indexOf("/>", nIdxStart);
        String strTmp = resp.substring(nIdxStart, nIdxEnd);
        String[] str = strTmp.split("\"");
        if (str.length >= 3) {
            authenticity_token_static = str[2];
        }

        List<String> cookies = req.getHeaderFields().get("Set-Cookie"); //get cookie
        //get cookie from response
        String[] cookieSplit;
        String[] cookieList;
        for (String listCookie : cookies) {
            cookieList = listCookie.split(";"); //split cookie by ; (example: asd=afsdf;domain=...)
            cookieSplit = cookieList[0].split("="); //example: split asd=afsdf by =
            if ("_fril_user_session".equals(cookieSplit[0])) {
                cookie_static = cookieList[0];
            }
        }
    }

    public static String GetMACAddress2() throws SocketException {
        Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
        String sMacAddress = "";
        while (nics.hasMoreElements()) {
            if (sMacAddress.isEmpty()) { // only return MAC Address from first card
                sMacAddress = nics.nextElement().getHardwareAddress().toString(); //return hardware address (usually MAC) of the interface
            }
        }
        return sMacAddress;
    }

    static void deleteItem(String strLink) throws IOException {
        Utility.getTokenToDeleteItem("https://fril.jp/sell");
        String[] strTmp = strLink.split("/");
        String strLink2 = "https://fril.jp/item/" + strTmp[strTmp.length -1 ];
        
        String formUrl = strLink2; // @"https://fril.jp/item/75371faa081354d19098b338bc7f7ed8";
        String formParams = "_method=delete&authenticity_token=" + URLEncoder.encode(token, "UTF-8");
        
        URL url = new URL(formUrl);
        HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
        req.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        req.setRequestProperty("Cookie", cookieID);
        req.setInstanceFollowRedirects(false);
        req.setRequestMethod("POST");
        req.setRequestProperty("Referer", "https://fril.jp/sell");
        req.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        
        byte[] bytes = formParams.getBytes(StandardCharsets.US_ASCII);
        req.setRequestProperty("Content-Length", String.valueOf(bytes.length));
        //Send POST request
        req.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(req.getOutputStream())) {
            wr.writeBytes(formParams);
            wr.flush();
            wr.close();
        }
        
    }

    static EditInfo getEditInfo(String selectedValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
