package com.example.management;

import com.example.management.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    User findAll();

    // Mapper 작성
    @Select("SELECT * FROM user WHERE id=#{id}")
    User findUserById(Integer id);
}
