package com.anjia.tumblr.responses;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.anjia.tumblr.TumblrClient;
import com.anjia.tumblr.types.Blog;
import com.anjia.tumblr.types.Post;
import com.anjia.tumblr.types.Resource;
import com.anjia.tumblr.types.TypeBlog;
import com.anjia.tumblr.types.User;

/**
 * <b>描　　述</b>: TODO<br>
 * <b>文件名称</b>: ResponseWrapper.java<br>
 * <b>包　　名</b>: com.anjia.tumblr.responses<br>
 * <b>创建时间</b>: 2016年12月4日 下午9:08:04<br> 
 * <b>修改时间</b>: <br> 
 *
 * @author SN_AnJia(anjia0532@qq.com)
 * @version 1.0
 * @since jdk 1.8
 */
public class ResponseWrapper {

	private JSONObject response;
	
	public JSONObject getResponse() {
		return response;
	}

	public void setResponse(JSONObject response) {
		this.response = response;
	}

	private TumblrClient client;

    public void setClient(TumblrClient client) {
        this.client = client;
    }

    public User getUser() {
        return get("user", User.class);
    }

    public Blog getBlog() {
        return get("blog", Blog.class);
    }
    public <T extends Post> TypeBlog<T> getTypeBlog(Class<T> k) {
        return getBlogByType(k);
    }

    public Post getPost() {
        return get("post", Post.class);
    }

    public Long getId() {
        return response.getLong("id");
    }

    // NOTE: needs to be duplicated logic due to Java erasure of generic types
    public List<Post> getPosts() {
        List<Post> l =response.getJSONArray("posts").toJavaObject(new TypeReference<List<Post>>(){}.getType());
        for (Post e : l) { e.setClient(client); }
        return l;
    }

    // NOTE: needs to be duplicated logic due to Java erasure of generic types
    public List<User> getUsers() {
    	List<User> l =response.getJSONArray("users").toJavaObject(new TypeReference<List<User>>(){}.getType());
        for (User e : l) { e.setClient(client); }
        return l;
    }

    // NOTE: needs to be duplicated logic due to Java erasure of generic types
    public List<Post> getLikedPosts() {
    	List<Post> l =response.getJSONArray("liked_posts").toJavaObject( new TypeReference<List<Post>>(){}.getType());
        for (Post e : l) { e.setClient(client); }
        return l;
    }

    // NOTE: needs to be duplicated logic due to Java erasure of generic types
    public List<Post> getTaggedPosts() {
    	List<Post> l =response.toJavaObject(new TypeReference<List<Post>>(){}.getType());
    	
        for (Post e : l) { e.setClient(client); }
        return l;
    }

    // NOTE: needs to be duplicated logic due to Java erasure of generic types
    public List<Blog> getBlogs() {
    	 List<Blog> l =response.getJSONArray("blogs").toJavaObject(new TypeReference<List<Blog>>(){}.getType());
        for (Blog e : l) { e.setClient(client); }
        return l;
    }

    private <T extends Resource> T get(String field, Class<T> k) {
        T e = JSON.parseObject(response.get(field).toString(),k);
        e.setClient(client);
        return e;
    }
    
	private <T extends Post> TypeBlog<T> getBlogByType(Class<T> k) {
        @SuppressWarnings("unchecked")
		TypeBlog<T> blog  = response.getJSONObject("blog").toJavaObject(TypeBlog.class);
        blog.setTypePostCount(response.getIntValue("total_posts"));
        
        List<T>  posts=response.getJSONArray("posts").toJavaObject( new TypeReference<List<Post>>(){}.getType());
        
        for (T post : posts) {post.setClient(client);}
        blog.setBlogPosts(posts);
        blog.setClient(client);
        blog.setTypePostCount(response.getIntValue("total_posts"));
        blog.setPostCount(response.getJSONObject("blog").getIntValue("total_posts"));
        return blog;
    }
}
