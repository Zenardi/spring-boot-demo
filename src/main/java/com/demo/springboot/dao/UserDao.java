package com.demo.springboot.dao;

import com.demo.springboot.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    List<User> selectAllUsers();
    Optional<User> selectUserByUserUid(UUID userUid);
    int updateUser(User user);
    int deleteUserByUserUid(UUID userUid);
    int insertUser(UUID userUid, User user);
}
