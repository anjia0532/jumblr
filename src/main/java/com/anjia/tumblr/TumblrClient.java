package com.anjia.tumblr;


import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.anjia.tumblr.exceptions.TumblrException;
import com.anjia.tumblr.responses.ResponseWrapper;
import com.anjia.tumblr.types.Blog;
import com.anjia.tumblr.types.Post;
import com.anjia.tumblr.types.TypeBlog;
import com.anjia.tumblr.types.User;
import com.anjia.tumblr.types.VideoPost;
import com.anjia.tumblr.utils.MultipartConverter;
import com.github.scribejava.apis.TumblrApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuthService;

/**
 * <b>描　　述</b>: TODO<br>
 * <b>文件名称</b>: TumblrClient.java<br>
 * <b>包　　名</b>: com.anjia.tumblr<br>
 * <b>创建时间</b>: 2016年12月4日 下午4:07:35<br> 
 * <b>修改时间</b>: <br> 
 *
 * @author SN_AnJia(anjia0532@qq.com)
 * @version 1.0
 * @since jdk 1.8
 */
public class TumblrClient {
	
	private String apiKey;

    private OAuth1AccessToken token;
    
    private OAuthService<OAuth1AccessToken> service;
	
	public TumblrClient() {}
	
	private Map<String, String> apiParams=new HashMap<>();
    /**
     * 实例化一个 令牌的 Tumblr Client 
     * 
     * @param consumerKey client的用户key 
     * @param consumerSecret client的 secret
     */
    public TumblrClient(String consumerKey, String consumerSecret) {
    	this();
    	apiKey=consumerKey;
    	apiParams.put("api_key", this.apiKey);
    	service=new ServiceBuilder()
                .apiKey(consumerKey)
                .apiSecret(consumerSecret)
                .build(TumblrApi.instance());
    }
    /**
     * 实例化 Tumblr client
     * @param consumerKey client的用户key 
     * @param consumerSecret client的 secret
     * @param token client的token
     * @param tokenSecret client的 token secret
     */
    public TumblrClient(String consumerKey, String consumerSecret, String token, String tokenSecret) {
    	this(consumerKey,consumerSecret);
    	setToken(token, tokenSecret);
    }

    public TumblrClient initService(String consumerKey, String consumerSecret) {
    	apiKey=consumerKey;
    	apiParams.put("api_key", this.apiKey);
    	service=new ServiceBuilder()
                .apiKey(consumerKey)
                .apiSecret(consumerSecret)
                .build(TumblrApi.instance());
    	return this;
    }
    /**
     * 给client 设置Token.
     * @param token client 的token.
     * @param tokenSecret client 的tokenSecret.
     */
    public TumblrClient setToken(String token, String tokenSecret) {
		this.token=new OAuth1AccessToken(token, tokenSecret);
		return this;
	}
    
    /**
     * 给client 设置Token.
     * @param token client 的Token.
     */
    public void setToken(final OAuth1AccessToken token) {
    	this.token=token;
    }
    
    
    /**
     * 根据博客地址/id 获取博客信息 
     * @param blogName 博客地址e.g. shaoniannule.tumblr.com 或者博客id e.g. shaoniannule
     * @return 返回Blog对象
     * @see https://www.tumblr.com/docs/en/api/v2#blog-info
     */
    public Blog blogInfo(String blogName) {
        return get(blogPath(blogName, "/info"), apiParams).getBlog();
    }
    
    /**
     * 获取具体尺寸的博客头像
     * @param blogName 博客地址(shaoniannule.tumblr.com或者shaoniannule)
     * @param size 下列值，默认 64  16, 24, 30, 40, 48, 64, 96, 128, 512
     * @return 字符串类型的头像地址
     * @see https://www.tumblr.com/docs/en/api/v2#blog-avatar
     */
    public String blogAvatar(String blogName, Integer size) {
        String pathExt = size == null ? "" : "/" + size.toString();
        return "https://api.tumblr.com/v2"+blogPath(blogName, "/avatar" + pathExt);
    }

    /**
     * 获取指定博客公开的喜欢的posts
     * @param blogName 博客地址
     * @param options 参数列表(可为空)
     * @return posts 集合
     * @see https://www.tumblr.com/docs/en/api/v2#blog-likes
     */
    public List<Post> blogLikes(String blogName, Map<String, Object> options) {
        if (options == null) {
            options = Collections.emptyMap();
        }
        options.put("api_key", this.apiKey);
        return get(blogPath(blogName, "/likes"), options).getLikedPosts();
    }
    
	/**
     * 博客Uri
     * @param blogName 博主名(博主id e.g. shaonian 或者博客首页 shaonian.tumblr.com) 
     * @param extPath 扩展路径
     * @return 拼接好的请求路径
     */
    static String blogPath(String blogName, String extPath) {
        return "/blog/" + blogUrl(blogName) + extPath;
    }

    /**
     * 获取关注指定博客的用户
     * @param blogName 博客名或者地址
     * @param options limit offset
     * @return 关注用户列表
     */
    public List<User> blogFollowers(String blogName, Map<String, ?> options) {
        return get(blogPath(blogName, "/followers"), options).getUsers();
    }
    
    /**
     * 获取关注指定博客的用户
     * @param blogName 博客名或者地址
     * @return 关注用户列表
     */
    public List<User> blogFollowers(String blogName) { return this.blogFollowers(blogName, null); }


    /**
     * 根据指定博客获取博客信息
     * @param blogName 博客名
     * @param options 参数列表
     * @param k 
     * @return The Blog object for this blog
     */
	@SuppressWarnings("rawtypes")
	public  <T extends Post> TypeBlog typeBlogInfo(String blogName,Map<String, Object> options,Class<T> k) {
        if (options == null) {
            options = new HashMap<>();
        }
        
        options.put("api_key", apiKey);
        
        String type = "";
        
        try {
			type="/"+k.newInstance().getType().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return get(blogPath(blogName, "/posts"+type.toLowerCase()), options).getTypeBlog(k);
    }

    /**
     * 获取指定博客的队列中的文章
     * @param blogName 博客名
     * @param options 
     * @return 文章列表
     */
    public List<Post> blogQueuedPosts(String blogName, Map<String, ?> options) {
        return get(blogPath(blogName, "/posts/queue"), options).getPosts();
    }
    
    /**
     * 获取指定博客的队列中的文章
     * @param blogName 博客名
     * @return 文章列表
     */
    public List<Post> blogQueuedPosts(String blogName) {
        return this.blogQueuedPosts(blogName, null);
    }


    /**
     * 获取指定博客的文章草稿列表
     * @param blogName 博客名
     * @param options 参数
     * @return 文章草稿列表
     */
    public List<Post> blogDraftPosts(String blogName, Map<String, ?> options) {
        return get(blogPath(blogName, "/posts/draft"), options).getPosts();
    }

    /**
     * 获取指定博客的文章草稿列表
     * @param blogName 博客名
     * @return 文章草稿列表
     */
    public List<Post> blogDraftPosts(String blogName) {
        return this.blogDraftPosts(blogName, null);
    }

    /**
     * 检索提交的帖子
     * @param blogName 博客名
     * @param options 请求参数
     * @return 提交的帖子列表
     */
    public List<Post> blogSubmissions(String blogName, Map<String, ?> options) {
        return get(blogPath(blogName, "/posts/submission"), options).getPosts();
    }

    /**
     * 检索提交的帖子
     * @param blogName 博客名
     * @return 提交的帖子列表
     */
    public List<Post> blogSubmissions(String blogName) {
        return this.blogSubmissions(blogName, null);
    }


    /**
     * 新建帖子
     * @param blogName 发帖的博客
     * @param detail 详细信息
     */
    public Long postCreate(String blogName, Map<String, Object> detail) throws IOException {
    	return postMultipart(blogPath(blogName, "/post"), detail).getId();
    }

    public void postEdit(String blogName, Long id, Map<String, Object> detail) throws IOException {
        if (detail==null) {
			detail=new HashMap<>();
		}
        detail.put("id", id);
        postMultipart(blogPath(blogName, "/post/edit"), detail);
    }
    


    /**
     * Reblog a given post
     * @param blogName the name of the blog to post to
     * @param postId the id of the post
     * @param reblogKey the reblog_key of the post
     * @param options Additional options (or null)
     */
    public Post postReblog(String blogName, Long postId, String reblogKey, Map<String, Object> options) {
        if (options == null) {
            options = new HashMap<String, Object>();
        }
        options.put("id", postId.toString());
        options.put("reblog_key", reblogKey);
        final Long reblogId = post(blogPath(blogName, "/post/reblog"), options).getId();
        return this.blogPost(blogName, reblogId);
    }

    public Post postReblog(String blogName, Long postId, String reblogKey) {
        return this.postReblog(blogName, postId, reblogKey, null);
    }
    


    /**
     * Get the posts for a given blog
     * @param blogName the name of the blog
     * @param options the options for this call (or null)
     * @return a List of posts
     */
    public List<Post> blogPosts(String blogName, Map<String, Object> options) {
        if (options == null) {
            options = new HashMap<>();
        }
        options.put("api_key", apiKey);

        String path = "/posts";
        if (options.containsKey("type")) {
            path += "/" + options.get("type").toString();
            options.remove("type");
        }
        return get(blogPath(blogName, path), options).getPosts();
    }

    public List<Post> blogPosts(String blogName) {
        return this.blogPosts(blogName, null);
    }

    /**
     * Get an individual post by id
     * @param blogName the name of the blog
     * @param postId the id of the post to get
     * @return the Post or null
     */
    public Post blogPost(String blogName, Long postId) {
        HashMap<String, Object> options = new HashMap<>();
        options.put("id", postId.toString());
        List<Post> posts = this.blogPosts(blogName, options);
        return posts.size() > 0 ? posts.get(0) : null;
    }

    /**
     * Delete a given post
     * @param blogName the name of the blog the post is in
     * @param postId the id of the post to delete
     */
    public void postDelete(String blogName, Long postId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", postId.toString());
        post(blogPath(blogName, "/post/delete"), map);
    }
    


    /**
     * Get the user info for the authenticated User
     * @return The authenticated user
     */
    public User user() {
        return get("/user/info", null).getUser();
    }

    /**
     * Get the user dashboard for the authenticated User
     * @param options the options for the call (or null)
     * @return A List of posts
     */
    public List<Post> userDashboard(Map<String, ?> options) {
        return get("/user/dashboard", options).getPosts();
    }

    public List<Post> userDashboard() {
        return this.userDashboard(null);
    }
    

    public List<Post> userLikes(Map<String, ?> options) {
        return get("/user/likes", options).getLikedPosts();
    }
    public List<Post> userLikes() {
        return this.userLikes(null);
    }


    /**
     * 获取该博主关注的博客列表
     * @param options offset query
     * @return 关注的博客列表
     */
    public List<Blog> userFollowing(Map<String, ?> options) {
        return get("/user/following", options).getBlogs();
    }

    /**
     * 获取该博主关注的博客列表
     * @return 关注的博客列表
     */
    public List<Blog> userFollowing() { return this.userFollowing(null); }


    /**
     * Follow a given blog
     * @param blogName The name of the blog to follow
     */
    public void follow(String blogName) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("url", blogUrl(blogName));
        post("/user/follow", map);
    }

    /**
     * Unfollow a given blog
     * @param blogName the name of the blog to unfollow
     */
    public void unfollow(String blogName) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("url", blogUrl(blogName));
        post("/user/unfollow", map);
    }

    /**
     * Like a given post
     * @param postId the ID of the post to like
     * @param reblogKey The reblog key for the post
     */
    public void like(Long postId, String reblogKey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", postId.toString());
        map.put("reblog_key", reblogKey);
        post("/user/like", map);
    }

    /**
     * Unlike a given post
     * @param postId the ID of the post to unlike
     * @param reblogKey The reblog key for the post
     */
    public void unlike(Long postId, String reblogKey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", postId.toString());
        map.put("reblog_key", reblogKey);
        post("/user/unlike", map);
    }

    /**
     * Set up a new post of a given type
     * @param blogName the name of the blog for this post (or null)
     * @param klass the type of Post to instantiate
     * @return the new post with the client set
     */
    public <T extends Post> T newPost(String blogName, Class<T> klass) throws IllegalAccessException, InstantiationException {
        T post = klass.newInstance();
        post.setClient(this);
        post.setBlogName(blogName);
        return post;
    }
    static String blogUrl(String blogName) {
        return StringUtils.contains(blogName, ".")? blogName : blogName + ".tumblr.com";
    }

    public ResponseWrapper get(String path, Map<String, ?> map) {
    	return clear(this.constructGet(path, map).send());
    }
    public ResponseWrapper post(String path, Map<String, ?> map) {
    	return clear(this.constructPost(path, map).send());
    }

	ResponseWrapper clear(Response response) {
		try {
			if (StringUtils.startsWith(String.valueOf(response.getCode()),"2")) {
				String json = response.getBody();
				ResponseWrapper wrapper = JSON.parseObject(json, ResponseWrapper.class);
				 if (wrapper == null) {
					 throw new TumblrException(response);
				 }
				wrapper.setClient(this);
				return wrapper;
			}
		} catch (Exception e) {
			throw new TumblrException(response);
		}
		throw new TumblrException(response);
	}
	
	public ResponseWrapper postMultipart(String path, Map<String, Object> bodyMap) throws IOException {
        OAuthRequest request = this.constructPost(path, bodyMap);
        OAuthRequest newRequest = new MultipartConverter(request, bodyMap).getRequest(service);
        return clear(newRequest.send());
    }
	
    public OAuthRequest constructGet(String path, Map<String, ?> queryParams) {
        String url = "https://api.tumblr.com/v2" + path;
        OAuthRequest request = new OAuthRequest(Verb.GET, url,service);
        service.signRequest(token, request);
        if (queryParams != null) {
            for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
                request.addQuerystringParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        request.addHeader("User-Agent", "tumblr v2");

        return request;
    }
    private OAuthRequest constructPost(String path, Map<String, ?> bodyMap) {
    	String url = "https://api.tumblr.com/v2" +  path;
        OAuthRequest request = new OAuthRequest(Verb.POST, url,service);
        service.signRequest(token, request);
        for (Map.Entry<String, ?> entry : bodyMap.entrySet()) {
        	String key = entry.getKey();
        	Object value = entry.getValue();
        	if (value == null || value instanceof File) { continue; }
            request.addBodyParameter(key,value.toString());
        }
        request.addHeader("User-Agent", "tumblr v2");
        return request;
    }
    public static void main(String[] args) throws IOException {

		System.setProperty("proxySet", "true");
		System.setProperty("proxyHost", "localhost");
		System.setProperty("proxyPort", "1080");
		
		TumblrClient tumblrClient=new TumblrClient("vCuTytzVQQ0scyP2A2X4Ig16rL4V7zzwPopROAUBBZTKXaQnHA", "xULUgkn92PHqK9Bw2nund9ItMnLyGGOtLLAxrJHA9qDbMHm4FR", "2u0ejTxzYbU7yI2K1aCM73uxYaEyAzb2gufbKtE2ffeFhpBjW2", "q6V8ooUBLEhDKzuTYSCXg0r9HcAyoYw5mwbNg9ZwKiZnadjnvb");
		System.err.println(tumblrClient.typeBlogInfo("shaoniannule", null, VideoPost.class).getPostCount());;
		//https://api.tumblr.com/v2/blog/john.io/posts/video?api_key={key}
//		System.err.println(tumblrClient.blogAvatar("shaoniannule",64));
	}
}
