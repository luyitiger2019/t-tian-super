package com.luyitian.son.member.mapper;


import com.luyitian.son.member.input.dto.UserInpDTO;
import com.luyitian.son.member.output.dto.UserOutDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Insert("INSERT INTO `meite_user` VALUES (null,#{mobile}, #{email}, #{password}, #{userName}, null, null, null, '1', null, null, null);")
    int register(UserInpDTO userEntity) ;

    @Select("SELECT * FROM meite_user WHERE MOBILE=#{mobile};")
    UserOutDTO existMobile(@Param("mobile") String mobile);
}
