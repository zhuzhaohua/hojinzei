package com.kobuta.rakuchin.hojinzei.rest;

import com.kobuta.rakuchin.hojinzei.oauth.entity.User;
import com.kobuta.rakuchin.hojinzei.service.UserService;
import com.kobuta.rakuchin.hojinzei.vo.TokenVo;
import com.kobuta.rakuchin.hojinzei.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRest {


    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public TokenVo login(@RequestBody Map<String, String> map) {

        String userName = map.get("username");
        String password = map.get("password");

        return userService.login(userName, password);
    }

    @GetMapping("/api/user/info")
    public UserVo getInfo(String token) {

        User user = userService.getInfo(token);

        return new UserVo(user);
    }

    @PostMapping("/api/user/logout")
    public String logout(String access_token) {

        if (userService.logout(access_token)) {
            return "注销成功";
        }
        else {
            return "注销失败";
        }
    }

}
