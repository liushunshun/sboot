package com.xy.mapper;

import com.xy.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by XiuYang on 2016/10/26.
 */
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE id = #{id}")
    User findById(Long id);

    @Select("SELECT * FROM USER WHERE email = #{email} limit 1")
    User findByEmail(String email);

    void insert(User user);
}
