package com.anjia.tumblr.types;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * Answer Post tests
 * @author jc
 */
public class AnswerPostTest extends TypeTest {

    private AnswerPost post;

    private String askingUrl = "asking_url";
    private String askingName = "asking_name";
    private String question = "question";
    private String answer = "answer";

    @Before
    public void setup() {
        Map<String, String> flat = new HashMap<String, String>();
        flat.put("type", "answer");
        flat.put("asking_url", askingUrl);
        flat.put("asking_name", askingName);
        flat.put("question", question);
        flat.put("answer", answer);
        post=JSON.parseObject(JSON.toJSONString(flat), new TypeReference<AnswerPost>(){});
    }

    @Test
    public void getAskingName() {
        assertEquals(askingName, post.getAskingName());
        assertEquals(askingUrl, post.getAskingUrl());
        assertEquals(question, post.getQuestion());
        assertEquals(answer, post.getAnswer());
    }

    @Test(expected=IllegalArgumentException.class)
    public void save() {
        post.save();
    }

}
