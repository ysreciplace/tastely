package org.ysreciplace.tastely.repository;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ysreciplace.tastely.entity.Verification;

@Mapper
public interface VerificationRepository {

    int create(Verification verification);

    Verification selectByToken(@Param("token") String token);
}
