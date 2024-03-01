package com.api.library.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.library.models.LibraryModel;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryModel, UUID>{

	boolean existsByBookName(String bookName);
	
	
}
