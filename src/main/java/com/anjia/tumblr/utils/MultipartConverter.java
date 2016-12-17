package com.anjia.tumblr.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.oauth.OAuthService;

/**
 * <b>描　　述</b>: TODO<br>
 * <b>文件名称</b>: MultipartConverter.java<br>
 * <b>包　　名</b>: com.anjia.tumblr.utils<br>
 * <b>创建时间</b>: 2016年12月8日 上午10:50:18<br> 
 * <b>修改时间</b>: <br> 
 *
 * @author SN_AnJia(anjia0532@qq.com)
 * @version 1.0
 * @since jdk 1.8
 */
/**
 * Convert a OAuthRequest POST into a multi-part OAuthRequest
 * @author jc
 */
public class MultipartConverter {

    private String boundary;
    private OAuthRequest originalRequest;

    private Integer bodyLength = 0;
    private List<byte[]> responsePieces;

    public MultipartConverter(OAuthRequest request, Map<String, ?> bodyMap) throws IOException {
        this.originalRequest = request;
        this.boundary = Long.toHexString(System.nanoTime());
        this.computeBody(bodyMap);
    }

    public OAuthRequest getRequest(OAuthService<OAuth1AccessToken> service) {
        OAuthRequest request = new OAuthRequest(originalRequest.getVerb(), originalRequest.getUrl(), service);
        request.addHeader("Authorization", originalRequest.getHeaders().get("Authorization"));
        request.addHeader("Content-Type", "multipart/form-data, boundary=" + boundary);
        request.addHeader("Content-length", bodyLength.toString());
        request.addPayload(complexPayload());
        return request;
    }

    private byte[] complexPayload() {
        int used = 0;
        byte[] payload = new byte[bodyLength];
        byte[] local;
        for (Object piece : responsePieces) {  
            local = (byte[]) piece;
            System.arraycopy(local, 0, payload, used, local.length);
            used += local.length;
        }
        return payload;
    }

    private void addResponsePiece(byte[] arr) {
        responsePieces.add(arr);
        bodyLength += arr.length;
    }

    private void addResponsePiece(StringBuilder builder) {
    	byte[] bytes = builder.toString().getBytes();
        responsePieces.add(bytes);
        bodyLength += bytes.length;
    }

    private void computeBody(Map<String, ?> bodyMap) throws IOException {
        responsePieces = new ArrayList<byte[]>();

        StringBuilder message = new StringBuilder();
        message.append("Content-Type: multipart/form-data; boundary=").append(boundary).append("\r\n\r\n");
        for (Map.Entry<String, ?> entry : bodyMap.entrySet()) {
        	String key = entry.getKey();
            Object object = entry.getValue();
            if (object == null) { continue; }
            if (object instanceof File) {
                File f = (File) object;
                String mime = URLConnection.guessContentTypeFromName(f.getName());

                byte[] result = new byte[(int)f.length()];
                
                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));
                try {
                    dis.readFully(result);
                } finally {
                    if (dis != null) {
                        dis.close();
                    }
                }

                message.append("--").append(boundary).append("\r\n");
                message.append("Content-Disposition: form-data; name=\"").append(key).append("\"; filename=\"").append(f.getName()).append("\"\r\n");
                message.append("Content-Type: ").append(mime).append("\r\n\r\n");
                this.addResponsePiece(message);
                this.addResponsePiece(result);
                message = new StringBuilder("\r\n");
            } else {
                message.append("--").append(boundary).append("\r\n");
                message.append("Content-Disposition: form-data; name=\"").append(key).append("\"\r\n\r\n");
                message.append(object.toString()).append("\r\n");
            }
        }

        message.append("--").append(boundary).append("--\r\n");
        this.addResponsePiece(message);
    }

}
