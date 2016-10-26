package com.xy.service;

import com.xy.bean.CurrentUser;
import com.xy.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * /*return new org.springframework.security.core.userdetails.User(
 username, user.getPassword(),
 true,//是否可用
 true,//是否过期
 true,//证书不过期为true
 true,//账户未锁定为true
 authorities);*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Override
    public CurrentUser loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByEmail(s);
        return new CurrentUser(user.orElseThrow(()->new UsernameNotFoundException(String.format("User with email=%s was not found",s))));
    }
}


