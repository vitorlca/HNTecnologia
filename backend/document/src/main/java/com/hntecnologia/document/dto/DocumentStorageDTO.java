package com.hntecnologia.document.dto;

import java.io.Serializable;

import com.hntecnologia.document.entities.DocumentStorage;

public class DocumentStorageDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String description;
	private String documentFile;
	
	public DocumentStorageDTO(Long id, String name, String description, String documentFile, String registration) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.documentFile = documentFile;
	}

	public DocumentStorageDTO(DocumentStorage entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.documentFile = entity.getDocumentFile();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentFile() {
		return documentFile;
	}

	public void setDocumentFile(String documentFile) {
		this.documentFile = documentFile;
	}


}
