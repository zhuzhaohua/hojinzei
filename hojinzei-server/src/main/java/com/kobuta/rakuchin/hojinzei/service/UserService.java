package com.kobuta.rakuchin.hojinzei.service;

import com.kobuta.rakuchin.hojinzei.oauth.entity.User;
import com.kobuta.rakuchin.hojinzei.vo.TokenVo;

public interface UserService {

    TokenVo login(String userName, String password);

    User getInfo(String token);

    boolean logout(String token);
}
