/**
 * 
 */
package com.cache.sbcache.config.session;

import com.cache.sbcache.config.session.CurrentUser;
import com.cache.sbcache.config.session.RedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author gannm
 *
 */
//@Service
//@Slf4j
public class RedisServerImpl implements RedisServer {

	@Resource(name="sessionRepository")
	private SessionRepository<ExpiringSession> sessionRepository;
	
	@Autowired
	private HttpServletRequest httpRequest;

	@Override
	public CurrentUser getCurrentUser(String sessionId) {
		CurrentUser currentUser = null;
		
/*		if(StringUtils.isBlank(sessionId)){
			log.error("getCurrentUser by sessionId :{} is blank 。 ",sessionId);
			throw new  RuntimeException("getCurrentUser by sessionId :" + sessionId+" is blank 。 ");
		}*/
		
		ExpiringSession expiringSession = sessionRepository.getSession(sessionId);	
		
	/*	if(expiringSession == null){
			log.error("getCurrentUser info is null  by sessionId :{}  ",sessionId);
			throw new  RuntimeException("getCurrentUser info is null  by sessionId:"+ sessionId);
		}*/


		Object object = expiringSession.getAttribute("userInfo");
		if(object != null && object instanceof CurrentUser){
			currentUser = (CurrentUser)object;
		}
	
		
		
		return currentUser;
	}

	@Override
	public CurrentUser getCurrentUser() {
		return getCurrentUser(httpRequest.getHeader("UserInfo"));
	}

}
