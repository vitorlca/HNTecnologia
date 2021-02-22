package com.hntecnologia.document.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hntecnologia.document.entities.DocumentStorage;

@Repository
public interface DocumentStorageRepository extends JpaRepository<DocumentStorage, Long> {

}
