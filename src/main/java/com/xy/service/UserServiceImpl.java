package com.xy.service;

import com.xy.bean.Role;
import com.xy.bean.User;
import com.xy.bean.UserCreateForm;
import com.xy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by XiuYang on 2016/10/25.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Async
    public Optional<User> getUserById(long id) {
        //return Optional.ofNullable(new User(1L,"admin@163.com","$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C", Role.ADMIN));
        return Optional.ofNullable(userMapper.findById(id));
    }

    @Async
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userMapper.findByEmail(email));
}

    @Async
    public Collection<User> getAllUsers() {
        return Arrays.asList(new User(1L,"admin@163.com","$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C", Role.ADMIN),new User(2L,"jlss2011@163.com","$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C", Role.USER));
    }

    @Async
    public User create(UserCreateForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setRole(form.getRole());
        userMapper.insert(user);
        return user;
    }
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("demo"));
    }
}
