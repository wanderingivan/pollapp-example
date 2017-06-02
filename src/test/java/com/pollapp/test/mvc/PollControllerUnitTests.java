package com.pollapp.test.mvc;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.pollapp.controller.PollRestController;
import com.pollapp.model.Option;
import com.pollapp.model.Poll;
import com.pollapp.model.User;
import com.pollapp.service.PollService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class PollControllerUnitTests extends AbstractControllerUnitTest {
    
    @Mock
    private PollService pollService;
    
    @InjectMocks
    private PollRestController controller;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                             .addFilter(new DelegatingFilterProxy("springSecurityFilterChain"), "/")
                             .build();
    }
    
    @Test
    public void testGetPoll() throws Exception {
        Poll poll = new Poll("name",Arrays.asList(new Option("opt1"),new Option("opt2")),new User(1,"username1"),"new description");
        poll.setId(1);
        
        when(pollService.getPollById(1)).thenReturn(poll);
        
        mvc.perform(get("/polls/1"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
           .andExpect(jsonPath("$.id", is(1)))
           .andExpect(jsonPath("$.name", is("name")))
           .andExpect(jsonPath("$.options", hasSize(2)))
           .andExpect(jsonPath("$.options[0].optionName", is("opt1")))
           .andExpect(jsonPath("$.options[1].optionName", is("opt2")))
           .andExpect(jsonPath("$.owner.username", is("username1")))
           .andExpect(jsonPath("$.description", is("new description")));
        
        verify(pollService, times(1)).getPollById(1);
        verifyNoMoreInteractions(pollService);
    }
    
    @Test
    public void testCreatePoll() throws Exception {
        Poll poll = new Poll("name",Arrays.asList(new Option("opt1"),new Option("opt2")),new User(0,"username1"),"new description");
        mvc.perform(post("/polls")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(poll))
                    .principal(() -> {return "username1";}))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        
        verify(pollService, times(1)).createPoll(poll);
        verifyNoMoreInteractions(pollService);
        
    }
    
    @Test
    public void testCreatePollActionDenied() throws Exception {
        Poll poll = new Poll("name",Arrays.asList(new Option("opt1"),new Option("opt2")),new User(1,"username1"),"new description");
        
        doThrow(new AccessDeniedException("")).when(pollService).createPoll(poll);
        
        mvc.perform(post("/polls")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(poll))
                    .principal(() -> {return "username1";}))
                    .andExpect(status().isInternalServerError());
        
        verify(pollService, times(1)).createPoll(poll);
        verifyNoMoreInteractions(pollService);
    }
    
    @Test
    public void testEditPoll() throws Exception {
        Poll poll = new Poll("name",Arrays.asList(new Option("opt1"),new Option("opt2")),new User(1,"username1"),"new description");
        poll.setId(1);
        
        mvc.perform(put("/polls/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(poll))
                    .principal(() -> {return "username1";}))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        
        verify(pollService, times(1)).editPoll(poll);
        verifyNoMoreInteractions(pollService);
    }
    
    @Test
    public void testEditPollActionDenied() throws Exception {
        Poll poll = new Poll("name",Arrays.asList(new Option("opt1"),new Option("opt2")),new User(1,"username1"),"new description");
        poll.setId(1);
        
        doThrow(new AccessDeniedException("")).when(pollService).editPoll(poll);
        
        mvc.perform(put("/polls/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(poll))
                    .principal(() -> {return "username1";}))
                    .andExpect(status().isInternalServerError());
        
        verify(pollService, times(1)).editPoll(poll);
        verifyNoMoreInteractions(pollService);
    }
}
