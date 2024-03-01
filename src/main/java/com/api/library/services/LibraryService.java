package com.api.library.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.library.models.LibraryModel;
import com.api.library.repositories.LibraryRepository;

import jakarta.transaction.Transactional;


@Service
public class LibraryService {
	
	final LibraryRepository libraryRepository;

	public LibraryService(LibraryRepository libraryRepository) {
		this.libraryRepository = libraryRepository;
	}
	
	public Page<LibraryModel> findAll(Pageable pageable) {
        return libraryRepository.findAll(pageable);
    }
	
	@Transactional
	public LibraryModel save(LibraryModel libraryModel) {
		return libraryRepository.save(libraryModel);
	}

	public boolean existsByBookName(String bookName) {
		return libraryRepository.existsByBookName(bookName);
	}
	
	public Optional<LibraryModel> findById(UUID id) {
		return libraryRepository.findById(id);
	}
	
	@Transactional
	public void delete(LibraryModel libraryModel) {
		libraryRepository.delete(libraryModel);
	}
}
