package com.hntecnologia.document.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hntecnologia.document.dto.DocumentStorageDTO;
import com.hntecnologia.document.entities.DocumentStorage;
import com.hntecnologia.document.repositories.DocumentStorageRepository;
import com.hntecnologia.document.services.exceptions.ResourceNotFoundException;

@Service
public class DocumentStorageService {
	
	@Autowired
	private DocumentStorageRepository repository;
	
	@Transactional(readOnly = true)
	public List<DocumentStorageDTO> findAll() {
		List<DocumentStorage> list = repository.findAll();
		List<DocumentStorageDTO> listDto = new ArrayList<>();
		for (DocumentStorage doc : list) {
			listDto.add(new DocumentStorageDTO(doc));
		}
		return listDto;
	}
	
	@Transactional(readOnly = true)
	public DocumentStorageDTO findById(Long id) {
		Optional<DocumentStorage> obj = repository.findById(id);
		DocumentStorage entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new DocumentStorageDTO(entity);
	}
	
	@Transactional
	public DocumentStorageDTO insert(DocumentStorageDTO dto) {
		DocumentStorage entity = new DocumentStorage();
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity = repository.save(entity);
		return new DocumentStorageDTO(entity);
	}
	
	@Transactional
	public DocumentStorageDTO update(Long id, DocumentStorageDTO dto) {
		try {
			DocumentStorage entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			entity = repository.save(entity);
			return new DocumentStorageDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	
	public Path saveDocument(MultipartFile documentFile, Long id){
		Optional<DocumentStorage> obj = repository.findById(id);
		DocumentStorage entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		String folder = "./uploads/";
		String fileName = generator() + documentFile.getOriginalFilename();
		try {
			if(entity.getDocumentFile() != null) {
				File file = new File(folder + entity.getDocumentFile()); 
				file.delete();
			}
			byte[] bytes = documentFile.getBytes();
			Path path = Paths.get(folder + fileName);	
			Files.write(path, bytes);
			entity.setDocumentFile(fileName);
			repository.save(entity);
			
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String generator() {
		Random random = new Random();
		return Integer.toString(random.hashCode());	
	}

}
