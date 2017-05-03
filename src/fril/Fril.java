/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fril;

import static fril.Utility.getProducts;
import static fril.Utility.trustAllCerts;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JFrame;

/**
 *
 * @author CuongPhan
 */
public class Fril {
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
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //TODO code application logic here
//        FrmLogin frm_login = new FrmLogin();
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.getContentPane().add(frm_login);
//        frame.pack();
//        frame.setVisible(true);

        //not use login form, hard code cookie
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        System.setProperty("proxyHost", "127.0.0.1");
        System.setProperty("proxyPort", "8888");
        Utility.gCookieID = "_fril_user_session=aG01M09yU09iRGNYb2RFSmZZNnNRME5NTDdWb3Vib3oxSmJOdTlneFZqanplREplR0p6amFqaGxobFlIdU9udU9jdjRqME94dnhOY3lHcmE1Z0R1RUNBNGdsODQ1VEdyK2xhcEdnQ21uTkdlRzE2MFl4d1FheUFML3AyZ1gvSVBWUkcyOUd6eER3RjF5aUFKQktvT2owbGp1T2VDZ1VEdVVKV1kwckRVVnVza3RpbFc2QmJQeTR6NUZpT2JYZWZiVlZwZ2JmeStkUGdYRjZEQmdFS0dBblNveVhpWXcwdlhlNUZRd1hnTE11SlErZllRSGxFTkFrb2xzbkFiK2pLSlJkWkVCemJIdGp3ejlJdyt5ZDBlOGtYTzlIM2VnSEt2Vm05eFFsNDNDSVR4RU1UZWN1ZGx4ZE1hM1BINm1FR1NmTTA5eGFzU2JCZ0hOWE9RR2NHMXpIU2t4OXBUUGFFZEpkcjA4RCs0RnpzbkJIdkh6aEN2U1FWb0RiZFc0YVl0LS12Wks1UmNJdVRHblpsQXg2SWlKUzFBPT0%3D--e1957d2401b1df72d1e239c2eb07e200e8726e08";
        ListItems listItems = new ListItems();
        listItems.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        listItems.pack();
        listItems.setVisible(true);
        //Utility.gCookieID ="_fril_user_session=Qjg4dElhS0NJbWc1ejluVXNsdUtQdlFJWnN6eFd6SkNNVUUxdmpvMldMM3dnTG5VZzlxVGgvdjZWY0NsMCt0RjdHN1J2UGNOZkRzVWZBeDNmZkRMUHkzMm1ITU1xSXNLRzN0SVgxNEN4eUNzZk5JUVo0c2RTc0hjVGp0N2lnMDY0Y0N2M09aaFBRWGtRVytNOXR4OHdBMzhSNDBGeVlUUUhYYk9EWW1EeDhuWm4zbStYbUtEVmE2cGtxRzljRVhHWWUxV282R0dXVnRlSkwzYUxwNE5vUDFkRWVKTTd1a0F6elhwRG5JUjZ1VE9RS1BuVHQzR1g2RXRTeUxzN2svbEg1dDJjUS9mVGg3VTNldWFNTGZrYTNMcUw4dGhjRHVHd2hkVGkvWTUrSHQ4ajVqcjI1Y1N5emU4TVRRbE9iMEVDOFVSbUozVWxYNFpnN3N1S3RjZXVkNVd1Wms4aXlpRFBGYjBDZmgvNWFiNzgvaVhJa0xzV3BVV05mVi9uVnRFLS0wSzI2MmxJNGJGdXFJd0laWEFaUUpRPT0%3D--fc88461dcb4b4dd8dc9872308280423538ec3c3a";
        //getProducts("https://fril.jp/ajax/item/selling");
    }

}
