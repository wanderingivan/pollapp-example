package com.pollapp.test.mvc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pollapp.controller.UserRestController;
import com.pollapp.model.User;
import com.pollapp.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class UserControllerUnitTests extends AbstractControllerUnitTest {

    @Mock
    private UserService userService;
    
    @InjectMocks
    private UserRestController controller;
    
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                             .build();
    }
    
    @Test
    public void testGetUsers() throws Exception {
        List<User> users = Arrays.asList(
                                         new User(1,"username1"),
                                         new User(2,"username2"));

        when(userService.findUsers("")).thenReturn(users);

        mvc.perform(get("/users"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
           .andExpect(jsonPath("$", hasSize(2)))
           .andExpect(jsonPath("$[0].id", is(1)))
           .andExpect(jsonPath("$[0].username", is("username1")))
           .andExpect(jsonPath("$[1].id", is(2)))
           .andExpect(jsonPath("$[1].username", is("username2")));

        verify(userService, times(1)).findUsers("");
        verifyNoMoreInteractions(userService);
    }
    
    @Test
    public void testGetUser() throws Exception {
        List<User> users = Arrays.asList(
                                         new User(1,"username1"),
                                         new User(2,"username2"));
        
        when(userService.getUserById(1)).thenReturn(users.get(0));
        when(userService.getUserById(2)).thenReturn(users.get(1));
        
        mvc.perform(get("/user/2"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
           .andExpect(jsonPath("$.id", is(2)))
           .andExpect(jsonPath("$.username", is("username2")));

        verify(userService, times(1)).getUserById(2);
        verifyNoMoreInteractions(userService);
    }
    
    @Test
    public void testCreateUser() throws Exception {
        User user = new User("username1", "email@email.com", "password", "description");

        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                        .andExpect(status().isOk());                    
        
        verify(userService, times(1)).createUser(user);
        verifyNoMoreInteractions(userService);
    }
    
    @Test
    public void testCreateUserAccessDenied() throws Exception {
        User user = new User("username1", "email@email.com", "password", "description");
 
        
        doThrow(new AccessDeniedException("")).when(userService).createUser(user);
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                        .andExpect(status().isInternalServerError());                    
        
        verify(userService, times(1)).createUser(user);
        verifyNoMoreInteractions(userService);
    }    
    
    @Test
    public void testEditUser() throws Exception {
        User user = new User("username1", "email@email.com", "password", "description");
        user.setId(1);
        
        
        mvc.perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                        .andExpect(status().isOk());                    
        
        verify(userService, times(1)).editUser(user);
        verifyNoMoreInteractions(userService);
    }    
    
    
    @Test
    public void testEditUserAccessDenied() throws Exception {
        User user = new User("username1", "email@email.com", "password", "description");
        user.setId(1);
        
        doThrow(new AccessDeniedException("")).when(userService).editUser(user);
        
        mvc.perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                        .andExpect(status().isInternalServerError());                    
        
        verify(userService, times(1)).editUser(user);
        verifyNoMoreInteractions(userService);
    }    
    
    @Test
    public void testFindUsers() throws Exception {
        List<User> users = Arrays.asList(
                                         new User(1,"username1"),
                                         new User(2,"username2"),
                                         new User(3,"another user"));

        when(userService.findUsers("user")).thenReturn(users.subList(0, 2));

        mvc.perform(get("/user/search/user"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
           .andExpect(jsonPath("$", hasSize(2)))
           .andExpect(jsonPath("$[0].id", is(1)))
           .andExpect(jsonPath("$[0].username", is("username1")))
           .andExpect(jsonPath("$[1].id", is(2)))
           .andExpect(jsonPath("$[1].username", is("username2")));

        verify(userService, times(1)).findUsers("user");
        verifyNoMoreInteractions(userService);
    }    

    @Test
    public void testFindUsersEmpty() throws Exception {

        when(userService.findUsers("user")).thenReturn(Collections.emptyList());

        mvc.perform(get("/user/search/user"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
           .andExpect(jsonPath("$", hasSize(0)));

        verify(userService, times(1)).findUsers("user");
        verifyNoMoreInteractions(userService);
    }    
    
}
