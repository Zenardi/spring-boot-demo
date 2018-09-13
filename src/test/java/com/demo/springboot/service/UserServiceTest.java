package com.demo.springboot.service;

import com.demo.springboot.dao.FakeDataDao;
import com.demo.springboot.model.User;
import jersey.repackaged.com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


public class UserServiceTest {

    @Mock
    private FakeDataDao fakeDataDao;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);
    }

    @Test
    public void shouldGetAllUsers() {
        UUID annaUserId = UUID.randomUUID();
        User anna = new User(annaUserId, "Anna", "Montana", User.Gender.FEMALE, 8, "anna@gmail.com");

        ImmutableList<User> users = new ImmutableList.Builder<User>().add(anna).build();

        given(fakeDataDao.selectAllUsers()).willReturn(users);
        List<User> allUsers = userService.getAllUsers();

        assertThat(allUsers).hasSize(1);

        User user = allUsers.get(0);
        assertUserFields(user);
    }



    @Test
    public void shouldGetUser() {
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid, "Anna", "Montana", User.Gender.FEMALE, 8, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(anna));

        Optional<User> userOptinal = userService.getUser(annaUid);

        assertThat(userOptinal.isPresent()).isTrue();
        User user = userOptinal.get();
        assertUserFields(user);
    }

    @Test
    public void updateUser() {
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid, "Anna", "Montana", User.Gender.FEMALE, 8, "anna@gmail.com");

        //mocking
        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(anna));
        given(fakeDataDao.updateUser(anna)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        //invoke service
        int updateResult = userService.updateUser(anna);
        verify(fakeDataDao.selectUserByUserUid(annaUid));
        verify(fakeDataDao.updateUser(captor.capture()));

        User user = captor.getValue();
        assertUserFields(user);

        assertThat(updateResult).isEqualTo(1);


    }

    @Test
    public void removeuser() {
    }

    @Test
    public void insertUser() {
    }


    private void assertUserFields(User user) {
        assertThat(user.getAge()).isEqualTo(8);
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getFirstName()).isEqualTo("Anna");
        assertThat(user.getUserId()).isNotNull();
    }
}