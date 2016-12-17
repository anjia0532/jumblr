package com.anjia.tumblr.types;

import java.util.List;

public class TypeBlog<T extends Post> extends Blog{

	private List<T> blogPosts;
	private int postCount;
	private String url;
	
	public  void setBlogPosts(List<T> posts) {
		this.blogPosts=posts;
	}

	public void setTypePostCount(int postCount) {
		this.postCount=postCount;
	}

	public int getTypePostCount() {
		return this.postCount ;
	}
	public  List<T> getBlogPosts() {
		return blogPosts;
	}

	public String getUrl() {
		return this.url;
	}
	
}
