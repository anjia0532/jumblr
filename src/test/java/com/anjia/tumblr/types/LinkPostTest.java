package com.anjia.tumblr.types;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * Tests for Link Post
 * @author jc
 */
public class LinkPostTest extends TypeTest {

    private LinkPost post;

    private String title = "hello";
    private String url = "tumblr.com";
    private String desc = "description";

    @Before
    public void setup() {
        Map<String, String> flat = new HashMap<String, String>();
        flat.put("type", "link");
        flat.put("title", title);
        flat.put("description", desc);
        flat.put("url", url);
        post=JSON.parseObject(JSON.toJSONString(flat), new TypeReference<LinkPost>(){});
    }

    @Test
    public void testReaders() {
        assertEquals(title, post.getTitle());
        assertEquals(url, post.getLinkUrl());
        assertEquals(desc, post.getDescription());
    }

    @Test
    public void testWriters() {
        post.setTitle("test_title");      assertEquals("test_title", post.getTitle());
        post.setLinkUrl("test_url");      assertEquals("test_url", post.getLinkUrl());
        post.setDescription("test_desc"); assertEquals("test_desc", post.getDescription());
    }

    @Test
    public void detail() {
        Map<String, Object> detail = post.detail();
        assertEquals(post.getTitle(), detail.get("title").toString());
        assertEquals(post.getLinkUrl(), detail.get("url").toString());
        assertEquals(post.getDescription(), detail.get("description").toString());
        assertEquals("link", detail.get("type"));
    }

}
