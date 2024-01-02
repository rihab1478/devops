package tn.esprit.spring.nemp.UserTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.nemp.Entities.User;
import tn.esprit.spring.nemp.Repositorys.UserRepository;
import tn.esprit.spring.nemp.Services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMockitoTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "12345678", 98765432, "John", "Doe", "example@example.com"));
        users.add(new User(2, "12345448", 53948594, "John", "Doe", "example@example.com"));

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    public void testAddUser() {
        User user = new User(1, "12345678", 98765432, "John", "Doe", "example@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals("John", result.getNom());
    }

    @Test
    public void testDeleteUser() {
        int userIdToDelete = 1;

        doNothing().when(userRepository).deleteById(userIdToDelete);

        userService.deleteUser(userIdToDelete);

        verify(userRepository, Mockito.times(1)).deleteById(userIdToDelete);
    }

    @Test
    @Disabled
    public void testUpdateUser() {
        User userToUpdate = new User(1, "12345678", 98765432, "John", "Doe", "example@example.com");

        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

        User updatedUser = userService.updateUser(1, userToUpdate);

        assertEquals("John", updatedUser.getNom());
        assertEquals("12345678", updatedUser.getCin());
        assertEquals(98765432, updatedUser.getTel());

        Mockito.verify(userRepository, Mockito.times(1)).save(userToUpdate);
    }
}
