package com.anjia.tumblr;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.anjia.tumblr.responses.ResponseWrapper;

/**
 * <b>描　　述</b>: TODO<br>
 * <b>文件名称</b>: TumblrClientTest.java<br>
 * <b>包　　名</b>: com.anjia.tumblr<br>
 * <b>创建时间</b>: 2016年12月11日 下午10:44:03<br> 
 * <b>修改时间</b>: <br> 
 *
 * @author SN_AnJia(anjia0532@qq.com)
 * @version 1.0
 * @since jdk 1.8
 */
public class TumblrClientTest1 {

    TumblrClient client;
    
    @Before
    public void setup() throws IOException {
        client = mock(TumblrClient.class);
        ResponseWrapper rw = new MockResponseWrapper();
        when(client.get(anyString(), anyMap())).thenReturn(rw);
        when(client.post(anyString(), anyMap())).thenReturn(rw);
        when(client.postMultipart(anyString(), anyMap())).thenReturn(rw);
    }

    @Test
    public void user() {
    	client.user();
        verify(client).get("/user/info", null).getUser();
    }
}
