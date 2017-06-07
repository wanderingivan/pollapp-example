package com.pollapp.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pollapp.service.ImageService;
import com.pollapp.service.UserService;

/**
 * Controller that handles image upload.
 * Calls user service to update the acting user's image on successfull
 * save
 */
@RestController
public class FileUploadController {

    private static Logger logger = Logger.getLogger(FileUploadController.class);
    private ImageService service;
    
    private UserService userService;
    
    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(@RequestBody MultipartFile file,Principal principal){
        try{
            String imageFilename = service.saveImage(file);
            userService.changeImage(principal.getName(),imageFilename);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            logger.error("Exception caught persisting image " + e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Autowired
    public void setService(ImageService service) {
        this.service = service;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
}
