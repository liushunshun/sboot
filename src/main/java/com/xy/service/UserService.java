package com.xy.service;

import com.xy.bean.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by XiuYang on 2016/10/25.
 */
public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();
}
