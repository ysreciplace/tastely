package org.ysreciplace.tastely.repository;

import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ysreciplace.tastely.entity.User;

import java.util.Optional;

@Mapper
public interface UserRepository {
    int save(User user);
    User findByUsernameOrEmail(String data);

    User findByProviderAndProviderId(@Param("provider") String provider,
                                     @Param("providerId") String providerId);

    int updatePasswordByEmail(@Param("email") String email,
                              @Param("password") String password);

    int updateUserVerified(String email);

    User findById(Long id);
}
