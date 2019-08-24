package com.kobuta.rakuchin.hojinzei.oauth.service;

import com.kobuta.rakuchin.hojinzei.oauth.entity.SecurityUser;
import com.kobuta.rakuchin.hojinzei.oauth.entity.User;
import com.kobuta.rakuchin.hojinzei.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Service;

@Service
public class OauthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices consumerTokenServices;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = this.findByUserName(s);

        return new SecurityUser(user.getUserName(), user.getPassword());
    }

    public User findByUserName(String username) {
        User user = userRepository.findByUserName(username);

        return user;
    }

    public User thisUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return this.findByUserName(auth.getName());
    }

    public boolean revokeToken(String token) {

        return consumerTokenServices.revokeToken(token);
    }
}
