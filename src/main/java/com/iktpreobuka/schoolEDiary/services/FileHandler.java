package com.iktpreobuka.schoolEDiary.services;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface FileHandler {
	public String userListUpload(MultipartFile file, RedirectAttributes redirectAttributes);
}
