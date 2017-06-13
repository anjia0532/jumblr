package com.anjia.tumblr.types;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;


/**
 * Tests for UnknownPost
 * @author jc
 */
public class UnknownTypePostTest extends TypeTest {

    private UnknownTypePost post;
    private Long id = 123L;

    @Before
    public void setup() {
        Map<String, Object> flat = new HashMap<String, Object>();
        flat.put("type", "nonexistent");
        flat.put("id", id);

        post=JSON.parseObject(JSON.toJSONString(flat), new TypeReference<UnknownTypePost>(){});
    }

    @Test
    public void isFound() {
        assertEquals(post.getId(), id);
    }

}
