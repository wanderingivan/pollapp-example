package com.pollapp.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pollapp.service.ImageService;

/**
 * A simple controller that returns images in a response body
 */
@Controller
public class ImageController {
	
	private static Logger logger = Logger.getLogger(ImageController.class);
	
	private ImageService imageService;
	
	@RequestMapping(value="/images/{imageName:.+}",method=RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getImage(@PathVariable String imageName){
		try{
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
		    File image = imageService.loadImage(imageName);
		    headers.setContentLength((int)image.length());    
		    FileInputStream in = new FileInputStream(image);
		    InputStreamResource out = new InputStreamResource(in);

			return new ResponseEntity<>(out,headers,HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught loading image with name " + imageName +"\n" + ex);
			
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	@Autowired
	public void setImageService(ImageService service){
		this.imageService = service;
	}
}
