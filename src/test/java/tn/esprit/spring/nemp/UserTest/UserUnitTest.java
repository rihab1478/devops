package tn.esprit.spring.nemp.UserTest;

import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.nemp.Entities.User;
import tn.esprit.spring.nemp.Excceptions.BadRequestException;
import tn.esprit.spring.nemp.Repositorys.UserRepository;
import tn.esprit.spring.nemp.Services.UserService;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Mocking userRepository behavior
        when(userRepository.findAll()).thenReturn(Arrays.asList(new User(), new User()));

        // Act
        var users = userService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() {
        // Mocking userRepository behavior
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));

        // Act
        var user = userService.getUserById(1);

        // Assert
        assertNotNull(user);
    }

    @Test
    void testCreateUser() {
        // Mocking userRepository behavior
        when(userRepository.selectExistsEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Act
        var user = userService.createUser(new User());

        // Assert
        assertNotNull(user);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // Given
        String address = "bizertetaken";  // Update the address to match the actual error message
        User user = new User(2, "11404196", 53948594, "rihab", "nabli", address);
        given(userRepository.selectExistsEmail(anyString())).willReturn(true);

        // When and Then
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Emailbizertetaken");  // Update the expected error message

        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdateUser() {
        // Mocking userRepository behavior
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Act
        var updatedUser = userService.updateUser(1, new User());

        // Assert
        assertNotNull(updatedUser);
    }

    @Test
    void testDeleteUser() {
        // Mocking userRepository behavior
        doNothing().when(userRepository).deleteById(1);

        // Act
        userService.deleteUser(1);

        // No assertion here, just making sure deleteById is called
        verify(userRepository, times(1)).deleteById(1);
    }
}
