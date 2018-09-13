package com.demo.springboot.dao;

import com.demo.springboot.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class FakeDataDaoTest {

    private FakeDataDao fakeDataDao;
    @Before
    public void setUp() throws Exception {
        fakeDataDao = new FakeDataDao();
    }

    @Test
    public void shouldSelectAllUsers() throws Exception {
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(1);
        User user = users.get(0);

        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getGender()).isEqualTo(User.Gender.MALE);
        assertThat(user.getFirstName()).isEqualTo("Joe");
        assertThat(user.getUserId()).isNotNull();
    }

    @Test
    public void shouldSelectUserByUserUid() throws Exception{
        UUID annaUserId = UUID.randomUUID();
        User user = new User(annaUserId, "Anna", "Montana", User.Gender.FEMALE, 8, "anna@gmail.com");
        fakeDataDao.insertUser(annaUserId, user);

        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);
        Optional<User> annaOptional = fakeDataDao.selectUserByUserUid(annaUserId);
        assertThat(annaOptional.isPresent()).isTrue();
        assertThat(annaOptional.get()).isEqualToComparingFieldByField(user);
    }

    @Test
    public void shouldNotSelectUserByUserUid() throws Exception{
        Optional<User> user = fakeDataDao.selectUserByUserUid(UUID.randomUUID());
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    public void shouldUpdateUser()throws Exception {
        UUID joeUuid = fakeDataDao.selectAllUsers().get(0).getUserId();
        User newJoe = new User(joeUuid, "Joe2", "Montana", User.Gender.FEMALE, 8, "anna@gmail.com");
        fakeDataDao.updateUser(newJoe);
        Optional<User> currentJoe = fakeDataDao.selectUserByUserUid(joeUuid);
        assertThat(currentJoe.isPresent()).isTrue();
        assertThat(fakeDataDao.selectAllUsers()).hasSize(1);
        assertThat(currentJoe.get()).isEqualToComparingFieldByField(newJoe);
    }

    @Test
    public void shouldDeleteUserByUserUid() throws Exception{
        UUID joeUuid = fakeDataDao.selectAllUsers().get(0).getUserId();
        fakeDataDao.deleteUserByUserUid(joeUuid);
        assertThat(fakeDataDao.selectUserByUserUid(joeUuid).isPresent()).isFalse();
        assertThat(fakeDataDao.selectAllUsers()).isEmpty();
    }

    @Test
    public void shouldInsertUser() throws Exception{
        UUID newUserUUid = UUID.randomUUID();
        User newUser = new User(newUserUUid, "Joe2", "Smith", User.Gender.MALE, 48, "joew@gmail.com");
        fakeDataDao.insertUser(newUserUUid, newUser);

        List<User> allUsers = fakeDataDao.selectAllUsers();
        assertThat(allUsers).hasSize(2);
        assertThat(fakeDataDao.selectUserByUserUid(newUserUUid).get()).isEqualToComparingFieldByField(newUser);
    }
}