package com.anjia.tumblr.types;

import com.anjia.tumblr.TumblrClient;

/**
 * A resource that can have a client
 * @author jc
 */
public class Resource {

    protected TumblrClient client;

    /**
     * Set the TumblrClient used for relative calls from this resource
     * @param client the client to use
     */
    public void setClient(TumblrClient client) {
        this.client = client;
    }

    /**
     * Get the client for a particular resource
     * @return the client
     */
    public TumblrClient getClient() {
        return this.client;
    }

}
