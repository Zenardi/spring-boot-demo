package com.demo.springboot.dao;

import com.demo.springboot.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
    public void selectUserByUserUid() throws Exception{
    }

    @Test
    public void updateUser()throws Exception {
    }

    @Test
    public void deleteUserByUserUid() throws Exception{
    }

    @Test
    public void insertUser() throws Exception{
    }
}