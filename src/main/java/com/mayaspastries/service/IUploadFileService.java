package com.mayaspastries.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	public Resource load(String filename) throws MalformedURLException;

	public String copy(MultipartFile file) throws IOException;

	public boolean delete(String filename);

	public String getImageNameById(int productId);

	public boolean deleteImageFromUploads(String imageName);

	public String saveImageToUploads(MultipartFile imageFile) throws IOException;

}
