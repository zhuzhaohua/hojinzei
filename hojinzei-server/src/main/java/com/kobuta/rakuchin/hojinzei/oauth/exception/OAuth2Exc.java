package com.kobuta.rakuchin.hojinzei.oauth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = OAuth2ExcJacksonSerializer.class)
public class OAuth2Exc extends OAuth2Exception {

	private static final long serialVersionUID = -5937621141324142626L;
	
	public OAuth2Exc(String msg, Throwable t) {
		super(msg,t);
	}
	public OAuth2Exc(String msg) {
		super(msg);
	}

}
