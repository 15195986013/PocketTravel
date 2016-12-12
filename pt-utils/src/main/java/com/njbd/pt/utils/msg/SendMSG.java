package com.njbd.pt.utils.msg;


import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Created by 李建成
 * on 16/12/6 .
 */
public class SendMSG {


    public static int sendMsg(String msg, String phone) throws Exception {
       /* HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
        NameValuePair[] data = {new NameValuePair("Uid", GetProperties.getSendUid()), new NameValuePair("Key", GetProperties.getSendKey()), new NameValuePair("smsMob", phone), new NameValuePair("smsText", msg + "【电话销售】")};
        post.setRequestBody(data);
        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        for (Header h : headers) {
            System.out.println(h.toString());
        }
        String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        post.releaseConnection();
        return Integer.valueOf(result);*/
       return 1;
    }
}
