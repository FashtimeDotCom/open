package org.open.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.Test;

import com.jscn.commons.core.http.HttpClientUtil;



public class OpenServiceTest {

//    @Test
    public void testService(){
        try {
            HttpClientUtil.get("http://localhost/open-web/order/orderService/query/xml/?orderId="+URLEncoder.encode("wwe点点滴滴eew","gbk")+"&partner=partner1&sign=df642736c3590796b4c59df243cecbae&inputCharset=GBK","GBK");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
//    @Test
    public void testCharset(){
        
        try {
            System.out.println(URLDecoder.decode("wwe%B5%E3%B5%E3%B5%CE%B5%CEeew", "GBK"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
