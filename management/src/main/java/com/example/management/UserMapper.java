package com.example.management;

import com.example.management.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<User> findAll();

    // Mapper 작성
    @Select("SELECT * FROM user WHERE id=#{id}")
    List<User> findUserById(Integer id);

    @Update("UPDATE user set name=#{formData.name}, age=#{formData.age}, email=#{formData.email}, phoneNum=#{formData.phoneNum} WHERE id=#{id}")
    void updateUserById(@Param("id") Integer id, @Param("formData") Map<String, Object>formData);

}
