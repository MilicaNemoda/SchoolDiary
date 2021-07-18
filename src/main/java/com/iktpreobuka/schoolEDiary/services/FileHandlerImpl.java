package com.iktpreobuka.schoolEDiary.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iktpreobuka.schoolEDiary.entities.UserEntity;
import com.iktpreobuka.schoolEDiary.repositories.UserRepository;



public class FileHandlerImpl implements FileHandler {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public String userListUpload(MultipartFile file, RedirectAttributes redirectAttributes) {
//
//		if (file.isEmpty()) {
//
//			redirectAttributes.addFlashAttribute("message", "No file selected! Select a file!");
//			return "redirect:fileTransferStatus";
//		}
//
//		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//
//			String line;
//			int skipped = 0;
//
//			while ((line = br.readLine()) != null) {
//
//				String[] tokens = line.split(",");
//
//				if (userRepository.findByEmail(tokens[1]).orElse(null) != null) {
//					skipped++;
//					continue;
//				}
//
//				UserEntity newUser = new UserEntity();
//
//				newUser.setName(tokens[0]);
//				newUser.setEmail(tokens[1]);
//				newUser.setCity(tokens[2]);
//				newUser.setExpenses(tokens[2].equals("Beograd") ? 10_000 : (tokens[2].equals("Novi Sad") ? 5000 : 0));
//
//				userRepository.save(newUser);
//			}
//			redirectAttributes.addFlashAttribute("message",
//					"User list successfully read! " + skipped + " entries skipped.");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return "redirect:/fileTransferStatus";
		return null;
	}

}
