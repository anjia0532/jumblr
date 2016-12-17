package com.anjia.tumblr.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.scribejava.core.model.Response;


/**
 * <b>描　　述</b>: TODO<br>
 * <b>文件名称</b>: TumblrException.java<br>
 * <b>包　　名</b>: com.anjia.tumblr.exceptions<br>
 * <b>创建时间</b>: 2016年12月4日 下午10:29:54<br> 
 * <b>修改时间</b>: <br> 
 *
 * @author SN_AnJia(anjia0532@qq.com)
 * @version 1.0
 * @since jdk 1.8
 */
public class TumblrException  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final int responseCode;
    private String message;
    private List<String> errors;

    /**
     * Instantiate a new JumblrException given a bad response to wrap
     * @param response the response to wrap
     */
    //{"meta":{"status":404,"msg":"Not Found"},"response":[]}
	public TumblrException(Response response) {
		this.responseCode = response.getCode();
		String body = null;
		try {
			 body= response.getBody();

			JSONObject element = JSON.parseObject(body);
			this.extractMessage(element);
			this.extractErrors(element);
		} catch (Exception ex) {
			this.message = body;
		}
	}

    /**
     * Get the HTTP response code for this error
     * @return the response code
     */
    public int getResponseCode() {
        return this.responseCode;
    }

    /**
     * Get the message for this error
     * @return the message
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * Get the errors returned from the API
     * @return List<String> errors (or null if none)
     */
    public List<String> getErrors() {
        return this.errors;
    }

    /**
     * Pull the errors out of the response if present
     * @param object the parsed response object
     */
    private void extractErrors(JSONObject object) {
        JSONObject response = object.getJSONObject("response");
        if (response == null) { return; }

        JSONArray e = response.getJSONArray("errors");
        if (e == null) { return; }

        // Set the errors
        errors = new ArrayList<String>(e.size());
        for (int i = 0; i < e.size(); i++) {
            errors.add(e.getString(i));
        }
    }

    /**
     * Pull the message out of the response
     * @param object the parsed response object
     */
    private void extractMessage(JSONObject object) {
        // Prefer to pull the message out of meta
        if (object.containsKey("meta")) {
            this.message = object.getJSONObject("meta").getString("msg");
            return;
        }

        // Fall back on error
        if (object.containsKey("error")) {
            this.message = object.getString("error");
            return;
        }

        // Otherwise set a default
        this.message = "Unknown Error";
    }

}
