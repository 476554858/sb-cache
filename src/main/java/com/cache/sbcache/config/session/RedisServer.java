/**
 * 
 */
package com.cache.sbcache.config.session;

import com.cache.sbcache.config.session.CurrentUser;

/**
 * @author gannm
 *
 */
public interface RedisServer {
	
	public CurrentUser getCurrentUser();
	
	CurrentUser getCurrentUser(String sessionId);
}
