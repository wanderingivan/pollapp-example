package com.pollapp.service;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


/**
 * 
 * Service Layer interface providing
 * basic save and retrieve operations
 * for images.
 * @throws IOException when missing files
 * or lacking credentials to save in directories 
 */
public interface ImageService {

	/**
	 * Retrieve an image as a base 64 string
	 * @param name of the file to use
	 * @return an image converted to a base 64 string
	 * @throws IOException
	 */
	String getB64(String name);
	
	/**
	 * Retrieve an image as a byte[]
	 * @param name the name of the image.<br/>
	 * This should not have to include the folder location!
	 * @return image converted as byte[]
	 */
	File loadImage(String name); 
	
	
	
	/**
	 * Saves an image to a predefined destination
	 * @param file
	 */
	String saveImage(MultipartFile file) throws IOException;
}
