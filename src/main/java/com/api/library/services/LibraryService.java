package com.api.library.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.library.models.LibraryModel;
import com.api.library.repositories.LibraryRepository;


@Service
public class LibraryService {
	
	final LibraryRepository libraryRepository;

	public LibraryService(LibraryRepository libraryRepository) {
		this.libraryRepository = libraryRepository;
	}
	
	public Page<LibraryModel> findAll(Pageable pageable) {
        return libraryRepository.findAll(pageable);
    }
	
}
