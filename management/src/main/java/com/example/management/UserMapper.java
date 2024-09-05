package com.example.management;

import com.example.management.model.SubjectDTO;
import com.example.management.model.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<UserDTO> findAll();

    @Select("SELECT * FROM user WHERE id=#{id}")
    UserDTO findUserById(Integer id);

    @Select("SELECT user.id, user.name, subject.code, subject.title FROM user INNER JOIN subject ON subject.code=user.subject_code WHERE user.id=#{id}")
    SubjectDTO findSubjectById(Integer id);

    @Update("UPDATE user set name=#{formData.name}, age=#{formData.age}, email=#{formData.email}, phoneNum=#{formData.phoneNum} WHERE id=#{formData.id}")
    Integer updateUserById(@Param("formData") UserDTO formData);


}
