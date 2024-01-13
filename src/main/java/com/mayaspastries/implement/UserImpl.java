package com.mayaspastries.implement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.mayaspastries.entities.User;
import com.mayaspastries.repository.UserRepository;
import com.mayaspastries.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserImpl implements UserService {

	private UserRepository repoUser;

	@Override
	public User getUserAndPassword(String user, String password) {
		// TODO Auto-generated method stub
		return repoUser.findByUsernameAndHashedPassword(user, password);
	}

	@Override
	public User getUser(String username, String providedPassword) {
		// TODO Auto-generated method stub
		User user = repoUser.findByUsernameAndHashedPassword(username, getSHA256Hash(providedPassword));
		if (user != null) {
			return user;
		}
		return null;
	}

	public static String getSHA256Hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(input.getBytes());

			StringBuilder hexString = new StringBuilder();
			for (byte hashByte : hashBytes) {
				hexString.append(String.format("%02x", hashByte));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
