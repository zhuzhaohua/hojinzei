package com.kobuta.rakuchin.hojinzei.oauth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

@Component
public class OAuth2ExcTranslator implements WebResponseExceptionTranslator<OAuth2Exc> {

	private static final Logger logger = LoggerFactory.getLogger(OAuth2ExcTranslator.class);
	@Override
	public ResponseEntity<OAuth2Exc> translate(Exception e) throws Exception {
		
		logger.error("Oauth2 异常：" + e.getMessage());
		
		OAuth2Exc exception = new OAuth2Exc(e.getMessage(),e);
		ResponseEntity<OAuth2Exc> response = new ResponseEntity<OAuth2Exc>(exception, HttpStatus.BAD_REQUEST);

		return response;
    }


}