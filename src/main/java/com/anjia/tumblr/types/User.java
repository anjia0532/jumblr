package com.anjia.tumblr.types;

import java.util.List;

/**
 * This class represents a User on Tumblr
 * @author jc
 */
public class User extends Resource {

    private List<Blog> blogs;
    private String name;
    private Object following;
    private Integer likes;
    private String default_post_format;

    /**
     * Return the default post format for this user
     * @return String format
     */
    public String getDefaultPostFormat() {
        return default_post_format;
    }

    /**
     * Get the name for this User object
     * @return The name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the number of likes for this user
     * @return the likes count
     */
    public Integer getLikeCount() {
        return this.likes;
    }

    /**
     * Get the number of users this user is following
     * @return The following count
     */
    public Integer getFollowingCount() {
        return following instanceof Boolean ? null : ((Integer) following).intValue();
    }

    /**
     * Determine if this user is following
     * @return An indication of following
     */
    public Boolean isFollowing() {
        return following instanceof Boolean ? (Boolean) following : null;
    }

    /**
     * Get the blog List for this user
     * @return The blog List for this user
     */
    public List<Blog> getBlogs() {
        if (blogs != null) {
            for (Blog blog : blogs) {
                blog.setClient(client);
            }
        }
        return this.blogs;
    }

	public Object getFollowing() {
		return following;
	}

	public void setFollowing(Object following) {
		this.following = following;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public String getDefault_post_format() {
		return default_post_format;
	}

	public void setDefault_post_format(String default_post_format) {
		this.default_post_format = default_post_format;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public void setName(String name) {
		this.name = name;
	}

}
