package com.anjia.tumblr;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.anjia.tumblr.exceptions.TumblrException;

public class TumblrClientErrorTest {

    TumblrClient client;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() throws IOException {
        client = new TumblrClient("ck", "cs", "@", "@");
        when(client.get(anyString(), anyMap())).thenThrow(TumblrException.class);
        when(client.post(anyString(), anyMap())).thenThrow(TumblrException.class);
        when(client.postMultipart(anyString(), anyMap())).thenThrow(TumblrException.class);
    }
    /**
     * User methods
     */

    @Test
    public void user() {
        thrown.expect(TumblrException.class);
        client.user();
    }

    @Test
    public void userDashboard() {
        thrown.expect(TumblrException.class);
        client.userDashboard();
    }

    @Test
    public void userFollowing() {
        thrown.expect(TumblrException.class);
        client.userFollowing();
    }

    @Test
    public void userLikes() {
        thrown.expect(TumblrException.class);
        client.userLikes();
    }

    @Test
    public void like() {
        Long id = 42L;
        String reblogKey = "hello";

        thrown.expect(TumblrException.class);
        client.like(id, reblogKey);
    }

    @Test
    public void unlike() {
        Long id = 42L;
        String reblogKey = "hello";

        thrown.expect(TumblrException.class);
        client.unlike(id, reblogKey);
    }

    @Test
    public void follow() {
        thrown.expect(TumblrException.class);
        client.follow("hey.com");
    }

    @Test
    public void unfollow() {
        thrown.expect(TumblrException.class);
        client.unfollow("hey.com");
    }

    /**
     * Blog methods
     */

    @Test
    public void userAvatar() {
        thrown.expect(TumblrException.class);
        client.blogAvatar("hey.com");
    }

    @Test
    public void blogInfo() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("api_key", "ck");

        thrown.expect(TumblrException.class);
        client.blogInfo("blog_name");
    }

    @Test
    public void blogFollowers() {
        thrown.expect(TumblrException.class);
        client.blogFollowers("blog_name");
    }

    @Test
    public void blogLikes() {
        thrown.expect(TumblrException.class);
        client.blogLikes("hey.com");
    }

    @Test
    public void blogPosts() {
        thrown.expect(TumblrException.class);
        client.blogPosts("hey.com");
    }

    @Test
    public void blogPost() {
        Long id = 24L;
        thrown.expect(TumblrException.class);
        client.blogPost("hey.com", id);
    }

    @Test
    public void blogQueuedPosts() {
        thrown.expect(TumblrException.class);
        client.blogQueuedPosts("hey.com");
    }

    @Test
    public void blogDraftPosts() {
        thrown.expect(TumblrException.class);
        client.blogDraftPosts("hey.com");
    }

    @Test
    public void blogSubmissions() {
        thrown.expect(TumblrException.class);
        client.blogSubmissions("hey.com");
    }

    /**
     * Post methods
     */

    @Test
    public void postDelete() {
        thrown.expect(TumblrException.class);
        client.postDelete("hey.com", 42L);
    }

    @Test
    public void postReblog() {
        thrown.expect(TumblrException.class);
        client.postReblog("hey.com", 42L, "key");
    }

    /**
     * Tagged methods
     */

    @Test
    public void tagged() {
        String tag = "coolio";

        thrown.expect(TumblrException.class);
        client.tagged(tag);
    }
}
