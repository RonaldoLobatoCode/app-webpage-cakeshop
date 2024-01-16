package com.mayaspastries.implement;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mayaspastries.service.IUploadFileService;

@Service
public class IUploadImpl implements IUploadFileService{

	private final static String UPLOADS_FOLDER = "C:\\uploads";
	
	@Override
	public Resource load(String filename) throws MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String filename) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getImageNameById(int productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteImageFromUploads(String imageName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String saveImageToUploads(MultipartFile imageFile) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
