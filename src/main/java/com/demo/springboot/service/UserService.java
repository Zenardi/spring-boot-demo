package com.demo.springboot.service;

import com.demo.springboot.dao.FakeDataDao;
import com.demo.springboot.dao.UserDao;
import com.demo.springboot.model.User;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.selectAllUsers();
    }


    public Optional<User> getUser(UUID userUid) {
        return userDao.selectUserByUserUid((userUid));
    }


    public int updateUser(User user) {
        Optional<User> optinalUser = getUser(user.getUserId());
        if(optinalUser.isPresent()){
            return userDao.updateUser(user);
        }
        return -1;
    }


    public int removeuser(UUID userUid) {
        Optional<User> optinalUser = getUser(userUid);
        if(optinalUser.isPresent()){
            return userDao.deleteUserByUserUid(userUid);

        }
        return -1;
    }


    public int insertUser(User user) {
        UUID userUid = UUID.randomUUID();
        user.setUserUuid(userUid);
        return userDao.insertUser(userUid, user);
    }
}
