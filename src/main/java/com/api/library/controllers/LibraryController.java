package com.api.library.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.library.models.LibraryModel;
import com.api.library.services.LibraryService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/library")
public class LibraryController {
	
	final LibraryService libraryService;

	
	public LibraryController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}
	
	
	@GetMapping
    public ResponseEntity<Page<LibraryModel>> getAllBooks(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(libraryService.findAll(pageable));
    }
}
