package com.webapp.webdemo.service;

import com.webapp.webdemo.entities.security.User;
import com.webapp.webdemo.repository.UserRepository;
import com.webapp.webdemo.service.impl.CustomUserDetailsServiceImpl;
import com.webapp.webdemo.service.impl.UserServiceImpl;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private CustomUserDetailsServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void getAccountByUsername() {
        String username = "bao";
        User user = new User();
        user.setUsername(username);

        when(userRepository.existsByUsername(username)).thenReturn(true);
        Assert.assertEquals(user, userService.loadUserByUsername(username));
    }
}
