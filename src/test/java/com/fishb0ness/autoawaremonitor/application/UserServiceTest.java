package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.user.User;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserName;
import com.fishb0ness.autoawaremonitor.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
    UserService userServiceMock = new UserService(userRepositoryMock);

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    public void testCreateUser() {
        UserName userName = new UserName("name");
        UserId userId = new UserId();
        User user = new User(userId, userName);
        when(userRepositoryMock.saveUser(any(User.class))).thenReturn(user);

        User result = userServiceMock.createUser(userName);

        assertEquals(user, result);
        verify(userRepositoryMock, times(1)).saveUser(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();
        assertThat(savedUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(user);
    }

    @Test
    public void testGetUserById() {
        UserId userId = new UserId();
        User user = new User(userId, new UserName("name"));
        Optional<User> userOptional = Optional.of(user);
        when(userRepositoryMock.getUser(userId)).thenReturn(userOptional);

        Optional<User> result = userServiceMock.getUserById(userId);

        assertEquals(userOptional, result);
        verify(userRepositoryMock, times(1)).getUser(userId);
    }

    @Test
    public void testGetAllUsers() {
        when(userRepositoryMock.getAllUsers()).thenReturn(List.of(new User(new UserId(), new UserName("name"))));

        List<User> result = userServiceMock.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepositoryMock, times(1)).getAllUsers();
    }

    @Test
    public void testUpdateUser() {
        User user = new User(new UserId(), new UserName("name"));
        when(userRepositoryMock.updateUser(user)).thenReturn(Optional.of(user));

        Optional<User> result = userServiceMock.updateUser(user);

        assertEquals(Optional.of(user), result);
        verify(userRepositoryMock, times(1)).updateUser(user);
    }

    @Test
    public void testDeleteUser() {
        UserId userId = new UserId();
        when(userRepositoryMock.deleteUser(userId)).thenReturn(1L);

        long result = userServiceMock.deleteUser(userId);

        assertEquals(1L, result);
        verify(userRepositoryMock, times(1)).deleteUser(userId);
    }
}
