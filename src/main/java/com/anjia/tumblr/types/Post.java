package com.anjia.tumblr.types;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

/**
 * This class is the base of all post types on Tumblr
 * @author jc
 */
public class Post extends Resource {

    /**
     * Enum of valid post types.
     */
    public enum PostType {
        TEXT("text"),
        PHOTO("photo"),
        QUOTE("quote"),
        LINK("link"),
        CHAT("chat"),
        AUDIO("audio"),
        VIDEO("video"),
        ANSWER("answer"),
        POSTCARD("postcard"),
        UNKNOWN("unknown");

        private final String mType;

        PostType(final String type) {
            this.mType = type;
        }

        public String getValue() {
            return this.mType;
        }
        public static PostType value(String type) {
			for (PostType val : values()) {
				if (val.mType.equals(type)) {
					return val;
				}
			}
			return PostType.UNKNOWN;
		}
    }

    private String blog_name;
    private Long id;
    private String post_url;
    protected PostType type;
    private Long timestamp;
    private String date;
    private String format;
    private String reblog_key;
    private List<String> tags;
    private Boolean bookmarklet;
    private Boolean mobile;
    private String short_url;
    private String source_url;
    private String source_title;
    private Boolean liked;
    private String state;
    
    private String author;
    private Long liked_timestamp;
    private String slug;
    private Long reblogged_from_id, reblogged_root_id;
    private String reblogged_from_url, reblogged_from_name, reblogged_from_title;
    private String reblogged_root_url, reblogged_root_name, reblogged_root_title;
    private Long note_count;
    private List<Note> notes;

    /**
     * Get the id of the author of the post
     * @return possibly null author id
     */
    public String getAuthorId() {
        return author;
    }

    /**
     * Get whether or not this post is liked
     * @return boolean
     */
    public Boolean isLiked() {
        return liked;
    }

    /**
     * Get the source title for this post
     * @return source title
     */
    public String getSourceTitle() {
        return source_title;
    }

    /**
     * Get the source URL for this post
     * @return source URL
     */
    public String getSourceUrl() {
        return source_url;
    }

    /**
     * Get whether or not this post was from mobile
     * @return boolean
     */
    public Boolean isMobile() {
        return mobile;
    }

    /**
     * Get whether or not this post was from the bookmarklet
     * @return boolean
     */
    public Boolean isBookmarklet() {
        return bookmarklet;
    }

    /**
     * Get the format for this post
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Get the current state for this post
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Get the post URL for this post
     * @return the URL
     */
    public String getPostUrl() {
        return post_url;
    }

    /**
     * Get the short URL for this post
     * @return the URL
     */
    public String getShortUrl() {
        return short_url;
    }

    /**
     * Get a list of the tags for this post
     * @return the tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Get the note count for this post
     * @return the note count
     */
    public Long getNoteCount() {
        return note_count;
    }

    /**
     * Get date of this post as String
     * @return date GMT string
     */
    public String getDateGMT() {
        return date;
    }

    /**
     * Get the timestamp of this post
     * @return timestamp since epoch
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Get timestamp of when this post was liked
     * @return the timestamp of when this post was liked
     */
    public Long getLikedTimestamp() { return liked_timestamp; }

    /**
     * Get the type of this post
     * @return type as String
     */
    public PostType getType() {
        return this.type;
    }

    /**
     * Get this post's ID
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the blog name
     * @return the blog name for the post
     */
    public String getBlogName() {
        return blog_name;
    }

    /**
     * Get the reblog key
     * @return the reblog key
     */
    public String getReblogKey() {
        return this.reblog_key;
    }

    /**
     * Get the slug
     * @return possibly null reblog key
     */
    public String getSlug() {
        return this.slug;
    }

    /**
     * Get the ID of the post that this post reblogged
     * @return the ID
     */
    public Long getRebloggedFromId() {
        return reblogged_from_id;
    }

    /**
     * Get name of the blog that this post reblogged
     * @return the blog name for the post that this post reblogged
     */
    public String getRebloggedFromName() {
        return reblogged_from_name;
    }

    /**
     * @return the url for the post that this post reblogged.
     */
    public String getRebloggedFromUrl() {
        return reblogged_from_url;
    }

    /**
     * @return the title for the post that this post reblogged.
     */
    public String getRebloggedFromTitle() {
        return reblogged_from_title;
    }

    /**
     * @return the root id for the post that this post reblogged.
     */
    public Long getRebloggedRootId() {
        return reblogged_root_id;
    }

    /**
     * @return the root url for the post that this post reblogged.
     */
    public String getRebloggedRootUrl() {
        return reblogged_root_url;
    }

    /**
     * @return the root name for the post that this post reblogged.
     */
    public String getRebloggedRootName() {
        return reblogged_root_name;
    }

    /**
     * @return the root title for the post that this post reblogged.
     */
    public String getRebloggedRootTitle() {
        return reblogged_root_title;
    }

    /**
     * Get the notes on this post. You must set "notes_info" to "true" in the
     * options map for this to work.
     * @return a copy of the array of the notes on this post
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Delete this post
     */
    public void delete() {
        client.postDelete(blog_name, id);
    }

    /**
     * Reblog this post
     * @param blogName the blog name to reblog onto
     * @param options options to reblog with (or null)
     * @return reblogged post
     */
    public Post reblog(String blogName, Map<String, Object> options) {
        return client.postReblog(blogName, id, reblog_key, options);
    }

    public Post reblog(String blogName) {
        return this.reblog(blogName, null);
    }

    /**
     * Like this post
     */
    public void like() {
        client.like(this.id, this.reblog_key);
    }

    /**
     * Unlike this post
     */
    public void unlike() {
        client.unlike(this.id, this.reblog_key);
    }

    /**
     * Set the blog name for this post
     * @param blogName the blog name to set
     */
    public void setBlogName(String blogName) {
        blog_name = blogName;
    }

    /**
     * Set the id for this post
     * @param id The id of the post
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Set the format
     * @param format the format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Set the slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * Set the data as a string
     * @param dateString the date to set
     */
    public void setDate(String dateString) {
        this.date = dateString;
    }

    /**
     * Set the date as a date
     * @param date the date to set
     */
    public void setDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        setDate(df.format(date));
    }

    /**
     * Set the state for this post
     * @param state the state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Set the tags for this post
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public void setType(String type) {
        this.type=PostType.value(type);
    }

    public String getBlog_name() {
		return blog_name;
	}

	public void setBlog_name(String blog_name) {
		this.blog_name = blog_name;
	}

	public String getPost_url() {
		return post_url;
	}

	public void setPost_url(String post_url) {
		this.post_url = post_url;
	}

	public String getReblog_key() {
		return reblog_key;
	}

	public void setReblog_key(String reblog_key) {
		this.reblog_key = reblog_key;
	}

	public Boolean getBookmarklet() {
		return bookmarklet;
	}

	public void setBookmarklet(Boolean bookmarklet) {
		this.bookmarklet = bookmarklet;
	}

	public Boolean getMobile() {
		return mobile;
	}

	public void setMobile(Boolean mobile) {
		this.mobile = mobile;
	}

	public String getShort_url() {
		return short_url;
	}

	public void setShort_url(String short_url) {
		this.short_url = short_url;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	public String getSource_title() {
		return source_title;
	}

	public void setSource_title(String source_title) {
		this.source_title = source_title;
	}

	public Boolean getLiked() {
		return liked;
	}

	public void setLiked(Boolean liked) {
		this.liked = liked;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getLiked_timestamp() {
		return liked_timestamp;
	}

	public void setLiked_timestamp(Long liked_timestamp) {
		this.liked_timestamp = liked_timestamp;
	}

	public Long getReblogged_from_id() {
		return reblogged_from_id;
	}

	public void setReblogged_from_id(Long reblogged_from_id) {
		this.reblogged_from_id = reblogged_from_id;
	}

	public Long getReblogged_root_id() {
		return reblogged_root_id;
	}

	public void setReblogged_root_id(Long reblogged_root_id) {
		this.reblogged_root_id = reblogged_root_id;
	}

	public String getReblogged_from_url() {
		return reblogged_from_url;
	}

	public void setReblogged_from_url(String reblogged_from_url) {
		this.reblogged_from_url = reblogged_from_url;
	}

	public String getReblogged_from_name() {
		return reblogged_from_name;
	}

	public void setReblogged_from_name(String reblogged_from_name) {
		this.reblogged_from_name = reblogged_from_name;
	}

	public String getReblogged_from_title() {
		return reblogged_from_title;
	}

	public void setReblogged_from_title(String reblogged_from_title) {
		this.reblogged_from_title = reblogged_from_title;
	}

	public String getReblogged_root_url() {
		return reblogged_root_url;
	}

	public void setReblogged_root_url(String reblogged_root_url) {
		this.reblogged_root_url = reblogged_root_url;
	}

	public String getReblogged_root_name() {
		return reblogged_root_name;
	}

	public void setReblogged_root_name(String reblogged_root_name) {
		this.reblogged_root_name = reblogged_root_name;
	}

	public String getReblogged_root_title() {
		return reblogged_root_title;
	}

	public void setReblogged_root_title(String reblogged_root_title) {
		this.reblogged_root_title = reblogged_root_title;
	}

	public Long getNote_count() {
		return note_count;
	}

	public void setNote_count(Long note_count) {
		this.note_count = note_count;
	}

	public String getDate() {
		return date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	/**
     * Add a tag
     */
    public void addTag(String tag) {
        if (this.tags == null) {
            tags = new ArrayList<String>();
        }
        this.tags.add(tag);
    }

    /**
     * Remove a tag
     */
    public void removeTag(String tag) {
        this.tags.remove(tag);
    }

    /**
     * Save this post
     */
    public void save() throws IOException {
        if (id == null) {
            this.id = client.postCreate(blog_name, detail());
        } else {
            client.postEdit(blog_name, id, detail());
        }
    }

    /**
     * Detail for this post
     * @return the detail
     */
    protected Map<String, Object> detail() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", state);
        map.put("tags", getTagString());
        map.put("format", format);
        map.put("slug", slug);
        map.put("date", date);
        map.put("type", getType().getValue());
        return map;
    }

    /**
     * Get the tags as a string
     * @return a string of CSV tags
     */
    private String getTagString() {
        return tags == null ? "" : StringUtils.join(tags.toArray(new String[0]), ",");
    }

    /**
     * Post toString
     * @return a nice representation of this post
     */
    @Override
    public String toString() {
        return "[" + this.getClass().getName() + " (" + blog_name + ":" + id + ")]";
    }

}
