package com.luyitian.son.member.mapper;

import com.luyitian.son.member.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Insert("INSERT INTO `meite_user` VALUES (null,#{mobile}, #{email}, #{password}, #{userName}, null, null, null, '1', null, null, null);")
    int register(UserEntity userEntity) ;

    @Select("SELECT * FROM meite_user WHERE MOBILE=#{mobile};")
    UserEntity existMobile(@Param("mobile") String mobile);
}
