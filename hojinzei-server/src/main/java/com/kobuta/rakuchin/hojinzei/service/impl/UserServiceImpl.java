package com.kobuta.rakuchin.hojinzei.service.impl;

import com.kobuta.rakuchin.hojinzei.oauth.entity.User;
import com.kobuta.rakuchin.hojinzei.oauth.service.OauthService;
import com.kobuta.rakuchin.hojinzei.service.UserService;
import com.kobuta.rakuchin.hojinzei.vo.TokenVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    OauthService oauthService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private String port;

    @Value("${oauth2.grantType}")
    private String grantType;

    @Value("${oauth2.scope}")
    private String scope;

    @Value("${oauth2.clientId}")
    private String clientId;

    @Value("${oauth2.secret}")
    private String secret;

    @Override
    public TokenVo login(String userName, String password) {
        logger.info("###tus#### get a login request : 【username】 " + userName);

        StringBuffer url = new StringBuffer();
        url.append("http://localhost:");
        url.append(port);
        url.append("/oauth/token");
        url.append("?username={username}&password={password}");
        url.append("&grant_type={grantType}&scope={scope}&client_id={clientId}&client_secret={secret}");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String,String>> request = new HttpEntity<>(headers);

        Map<String,String> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", password);
        params.put("grantType", grantType);
        params.put("scope", scope);
        params.put("clientId", clientId);
        params.put("secret", secret);
        ResponseEntity<TokenVo> response = restTemplate.exchange(url.toString(), HttpMethod.POST,request, TokenVo.class,params);

        return  response.getBody();
    }

    @Override
    public User getInfo(String token) {

        return oauthService.thisUser();
    }

    @Override
    public boolean logout(String token) {
        return oauthService.revokeToken(token);
    }
}
