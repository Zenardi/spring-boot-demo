package com.demo.springboot.service;

import com.demo.springboot.dao.UserDao;
import com.demo.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(Optional<String> gender) {
        List<User> users = userDao.selectAllUsers();

        if(!gender.isPresent()){
            return users;
        }
        try {
            User.Gender theGender = User.Gender.valueOf(gender.get().toUpperCase()); //MALE or male
            return users.stream().filter(user -> user.getGender().equals(theGender)).collect(Collectors.toList());
        }
        catch (Exception e){
            throw new IllegalStateException("Invalid Gender" ,e);
        }
    }


    public Optional<User> getUser(UUID userUid) {
        return userDao.selectUserByUserUid((userUid));
    }


    public int updateUser(User user) {
        Optional<User> optinalUser = getUser(user.getUserUid());
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
        return userDao.insertUser(userUid, User.newUser(userUid, user));
    }
}
