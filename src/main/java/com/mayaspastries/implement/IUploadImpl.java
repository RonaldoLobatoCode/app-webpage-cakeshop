package com.mayaspastries.implement;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mayaspastries.entities.Product;
import com.mayaspastries.repository.ProductRepository;
import com.mayaspastries.service.IUploadFileService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class IUploadImpl implements IUploadFileService{

	private final static String UPLOADS_FOLDER = "C:\\uploads";
	
	private ProductRepository repoProduct;
	
	@Override
	public Resource load(String filename) throws MalformedURLException {
		// TODO Auto-generated method stub
		Path path = getPath(filename);
		Resource resource = new UrlResource(path.toUri());
		if(!resource.exists() || !resource.isReadable()) {
			throw new RuntimeException("Error in path : " + path.toString());
		}
		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rooPath = getPath(uniqueFileName);
		Files.copy(file.getInputStream(), rooPath);
		return uniqueFileName;
	}

	@Override
	public boolean delete(String filename) {
		// TODO Auto-generated method stub
		Path rootPath = getPath(filename);
		File file = rootPath.toFile();
		if(file.exists() && file.canExecute()) {
			return true;
		}
		return false;
	}

	@Override
	public String getImageNameById(int productId) {
		// TODO Auto-generated method stub
		Product product = repoProduct.findById(productId).orElse(null);
		
		if(product != null) {
			return product.getImage();
		}
		return null;
	}

	@Override
	public boolean deleteImageFromUploads(String imageName) {
		// TODO Auto-generated method stub
		try {
			String uploadsDirectory = "uploads";
			File file = new File(uploadsDirectory, imageName);
			
			if(file.exists()) {
				if(file.delete()) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String saveImageToUploads(MultipartFile imageFile) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }
}
