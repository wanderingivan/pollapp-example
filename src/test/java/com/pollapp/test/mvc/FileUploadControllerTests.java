package com.pollapp.test.mvc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pollapp.controller.FileUploadController;
import com.pollapp.service.ImageService;
import com.pollapp.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


public class FileUploadControllerTests extends AbstractControllerUnitTest {
    
    @Mock
    private ImageService iService;
    
    @Mock
    private UserService uService;
    
    @InjectMocks
    private FileUploadController controller;
    
    
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                             .build();
    }
    
    @Test
    public void testUpload() throws Exception{
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", "test.jpg", "image/jpg", "Spring Framework".getBytes());
        
        when(iService.saveImage(multipartFile)).thenReturn("test.jpg");
        
        this.mvc.perform(fileUpload("/upload").file(multipartFile)
                                              .principal(() -> "username1"))
                .andExpect(status().isOk());
                
                verify(iService,times(1)).saveImage(multipartFile);
                verify(uService,times(1)).changeImage("username1", "test.jpg");
                verifyNoMoreInteractions(iService,uService);
        
    }
}
