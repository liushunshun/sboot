package com.xy.service;

import com.xy.bean.Role;
import com.xy.bean.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by XiuYang on 2016/10/25.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(new User(1L,"admin@163.com","$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C", Role.ADMIN));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(new User(1L,"admin@163.com","$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C", Role.ADMIN));
}

    @Override
    public Collection<User> getAllUsers() {
        return Arrays.asList(new User(1L,"admin@163.com","$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C", Role.ADMIN),new User(2L,"jlss2011@163.com","$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C", Role.USER));
    }
}
