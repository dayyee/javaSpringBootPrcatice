package com.example.management;

import com.example.management.model.SubjectDTO;
import com.example.management.model.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT id, name, age, email, phoneNum FROM user")
    List<UserDTO> findAll();

    @Select("SELECT id, name, age, email, phoneNum FROM user WHERE id=#{id}")
    UserDTO findUserById(Integer id);

    @Select("SELECT user.id, user.name, subject.code, subject.title FROM user INNER JOIN subject ON subject.code=user.subject_code WHERE user.id=#{id}")
    SubjectDTO findSubjectById(Integer id);

    @Update("UPDATE user set name=#{formData.name}, age=#{formData.age}, email=#{formData.email}, phoneNum=#{formData.phoneNum} WHERE id=#{formData.id}")
    Integer updateUserById(@Param("formData") UserDTO formData);

    @Delete("DELETE FROM user WHERE id=#{id}")
    Integer removeUserById(Integer id);

    @Select("SELECT id, name, age, email, phoneNum FROM user WHERE id=#{id} OR name=#{name}")
    UserDTO findDataByKeyWord(@Param("id") Integer id, @Param("name") String name);

}
