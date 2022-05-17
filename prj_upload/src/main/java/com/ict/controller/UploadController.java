package com.ict.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ict.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j //pom.xml에서 junit 4.12로, log4j는 1.2.17버전, exclusions 태그 삭제, scope 주석
public class UploadController {
	
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
	
		return str.replace("-", File.separator);
	}
	

	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
	
	@PostMapping(value="/uploadFormAction",
				produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> upladFormPost(MultipartFile[] uploadFile) {
		
		// AttachFileDTO는 파일 한 개의 정보를 저장합니다.
		// 현재 파일 업로드는 여러 파일을 동시에 업로드하므로 List<AttachFileDTO>를 받도록 처리합니다.
		List<AttachFileDTO> list = new ArrayList<>();
		
		// 어떤 폴더에 저장할것인지 위치지정
		String uploadFolder = "C:\\upload_data\\temp";
		
		// 폴더 경로 받아오기
		String uploadFolderPath = getFolder();
		
		// 폴더 생성 로직
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path : " + uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("--------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			// 파일정보를 저장할 DTO 생성
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("last file name : " + uploadFileName);
			
			// 상단에 만든 DTO에 파일이름 저장
			attachDTO.setFileName(uploadFileName);
			
			// uuid 발급 부분
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			
			//File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				// uuid와 저장할 폴더 경로를 setter로 입력받기
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				// 이 아래부터 썸네일 생성로직
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail =
						new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}

				// ArrayList에 개별 DTO를 집어넣어줘야 출력됨 
				list.add(attachDTO);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}// end for
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {

		log.info("upload ajax");
	}
	
	
	// uploadFormAction과 유사하게 AttachDTO를 받도록 32~35를 보고 수정해주세요.
	@PostMapping(value="/uploadAjaxAction",
				produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody// rest컨트롤러 메서드로 변경
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
		log.info("ajax post update!");

		List<AttachFileDTO> list = new ArrayList<>();
		
		String uploadFolder = "C:\\upload_data\\temp";
		
		String uploadFolderPath = getFolder();
		
		// 폴더 생성 로직
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path : " + uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("==============================");
			log.info("upload file name : " + multipartFile.getOriginalFilename());
			log.info("Upload file size : " + multipartFile.getSize());
			
			// 개별 파일에 대한 DTO 생성
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("last file name : " + uploadFileName);
			
			// uploadFileName에 의해 파일이름을 얻어왔으면 파일명을 attachDTO에 집어넣음
			attachDTO.setFileName(uploadFileName);
			
			// uuid 발급 부분
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			//File saveFile = new File(uploadFolder, uploadFileName);
			
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				// 이 아래부터 썸네일 생성로직
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail =
						new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
				
				list.add(attachDTO);
				
			} catch(Exception e) {
				log.error(e.getMessage());
			}
		}//endfor
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	
}
