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
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    public void shouldGetAllUsers() throws Exception{
        UUID annaUserId = UUID.randomUUID();
        User anna = new User(annaUserId, "Anna", "Montana", User.Gender.FEMALE, 8, "anna@gmail.com");

        ImmutableList<User> users = new ImmutableList.Builder<User>().add(anna).build();

        given(fakeDataDao.selectAllUsers()).willReturn(users);
        List<User> allUsers = userService.getAllUsers(Optional.empty());

        assertThat(allUsers).hasSize(1);

        User user = allUsers.get(0);
        assertAnnaFields(user);
    }

    @Test
    public void shouldGetAllUsersByGender() {
        UUID annaUserId = UUID.randomUUID();
        User anna = new User(annaUserId, "Anna", "Montana", User.Gender.FEMALE, 8, "anna@gmail.com");

        UUID joeaUserId = UUID.randomUUID();
        User joe = new User(joeaUserId, "joe", "smith", User.Gender.MALE, 38, "joe@gmail.com");

        ImmutableList<User> users = new ImmutableList.Builder<User>().add(anna).add(joe).build();

        given(fakeDataDao.selectAllUsers()).willReturn(users);

        List<User> filteredUsers = userService.getAllUsers(Optional.of("female"));
        assertThat(filteredUsers).hasSize(1);
        assertAnnaFields(filteredUsers.get(0));
    }

    @Test
    public void shouldGetUser() throws Exception{
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid, "Anna", "Montana", User.Gender.FEMALE, 8, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(anna));

        Optional<User> userOptinal = userService.getUser(annaUid);

        assertThat(userOptinal.isPresent()).isTrue();
        User user = userOptinal.get();
        assertAnnaFields(user);
    }

    @Test
    public void souldUpdateUser() throws Exception{
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid, "Anna", "Montana", User.Gender.FEMALE, 8, "anna@gmail.com");

        //mocking
        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(anna));
        given(fakeDataDao.updateUser(anna)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        //invoke service
        int updateResult = userService.updateUser(anna);
        verify(fakeDataDao).selectUserByUserUid(annaUid);
        verify(fakeDataDao).updateUser(captor.capture());

        User user = captor.getValue();
        assertAnnaFields(user);

        assertThat(updateResult).isEqualTo(1);
    }

    @Test
    public void ShouldThrowsExeptionInvalidGender() throws Exception {
        assertThatThrownBy(()->userService.getAllUsers(Optional.of("MAALE"))).isInstanceOf(IllegalStateException.class).hasMessage("Invalid Gender");
    }

    @Test
    public void shouldRemoveuser()throws Exception {
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid, "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(anna));
        given(fakeDataDao.deleteUserByUserUid(annaUid)).willReturn(1);


        int deleteResult = userService.removeuser(annaUid);

        verify(fakeDataDao).selectUserByUserUid(annaUid);
        verify(fakeDataDao).deleteUserByUserUid(annaUid);

        assertThat(deleteResult).isEqualTo(1);
    }

    @Test
    public void shouldInsertUser() throws Exception{
        User anna = new User(null, "Anna", "Montana", User.Gender.FEMALE, 8, "anna@gmail.com");

        given(fakeDataDao.insertUser(any(UUID.class), eq(anna))).willReturn(1);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        int insertResult = userService.insertUser(anna);

        verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());
        User user = captor.getValue();

        assertAnnaFields(user);

        assertThat(insertResult).isEqualTo(1);

    }


    private void assertAnnaFields(User user) {
        assertThat(user.getAge()).isEqualTo(8);
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getFirstName()).isEqualTo("Anna");
        assertThat(user.getUserUid()).isNotNull();
        assertThat(user.getUserUid()).isInstanceOf(UUID.class);

    }
}