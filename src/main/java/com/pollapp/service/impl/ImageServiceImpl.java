package com.pollapp.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.pollapp.service.ImageService;
import com.pollapp.util.ImageUtil;

/** 
 *	Implementation of ImageService
 *	that adds caching for images 
 *  and delegates image manipulation to ImageUtil.
 *  This class will also attempt to return preset placeholder
 *  images when a requested image is unavailable
 *  @see ImageService
 *  @see  ImageUtil
 *
 */
public class ImageServiceImpl implements ImageService {
	
	private String placeholderFilename;
	private ImageUtil imageUtil;
	
	@Autowired
	public ImageServiceImpl(ImageUtil imageUtil,String placeholderFilename){
		super();
		this.imageUtil = imageUtil;
		this.placeholderFilename = placeholderFilename;
	}
	
	/**
	 * Uses image util to return an image converted as a b64 string.<br/>
	 * Tries to resolve missing files by returning a placeholder
	 * image
	 */
	@Override
	@Cacheable(value="b64", key="#path")
	public String getB64(String path){
		try{
			return imageUtil.encodeToB64String(path);
		}catch(IOException missingFile){
			System.out.println("Cant open file "+ path);
			try{
			    return imageUtil.encodeToB64String(placeholderFilename);
			}catch(IOException ignore){
				throw new RuntimeException("Can't open placeholder file ");
			}
		}
	}

	/**
	 * Uses image util to return an image loaded in a byte[].<br/>
	 * Tries to resolve missing files by returning a placeholder
	 * image
	 */
	@Override
	@Cacheable(value="image", key="#path")
	public File loadImage(String path){
		try{
			return imageUtil.loadImage(path);
		}catch(IOException missingFile){
			System.out.println("Cant open file "+ path);
			try{
			    return imageUtil.loadImage(placeholderFilename);
			}catch(Exception ignore){
				throw new RuntimeException("Can not open placeholder file ");
			}
		}
	}

	@Override
	public String saveImage(File file, String contentType, String fileName) throws IOException{
		 return imageUtil.saveImage(file, contentType, fileName);
	}

}