package com.kobuta.rakuchin.hojinzei.vo;

import com.kobuta.rakuchin.hojinzei.oauth.entity.User;
import org.springframework.util.StringUtils;

public class UserVo {

    private String[] roles = {};
    private String username;
    private String nickname;

    public UserVo() {}

    public UserVo(User user) {
        this.username = user.getUserName();
        String strRoles = user.getRoles();
        if (!StringUtils.isEmpty(strRoles)) {
            this.roles =  strRoles.split(",");
        }

    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
