package com.api.library.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.library.dtos.LibraryDto;
import com.api.library.models.LibraryModel;
import com.api.library.services.LibraryService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/library")
public class LibraryController {
	
	final LibraryService libraryService;

	
	public LibraryController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}
	
	
	@PostMapping
    public ResponseEntity<Object> saveBook(@RequestBody @Valid LibraryDto libraryDto){
		 if(libraryService.existsByLicenseBookName(libraryDto.getBookName())){
	          return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Book name is already in use!");
	        }
        var libraryModel = new LibraryModel();
        BeanUtils.copyProperties(libraryDto, libraryModel);
        libraryModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryService.save(libraryModel));
    }
	
	
	@GetMapping
    public ResponseEntity<Page<LibraryModel>> getAllBooks(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(libraryService.findAll(pageable));
    }
	
	
	@GetMapping("/{id}")
    public ResponseEntity<Object> getOneLibrary(@PathVariable(value = "id") UUID id){
        Optional<LibraryModel> libraryModelOptional = libraryService.findById(id);
        if (!libraryModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(libraryModelOptional.get());
    }
	
	
	@PutMapping("/{id}")
    public ResponseEntity<Object> updateLibrary(@PathVariable(value = "id") UUID id,@RequestBody @Valid LibraryDto libraryDto){
      Optional<LibraryModel> libraryModelOptional = libraryService.findById(id);
      if (!libraryModelOptional.isPresent()) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
      }
      var libraryModel = new LibraryModel();
      BeanUtils.copyProperties(libraryDto, libraryModel);
      libraryModel.setId(libraryModelOptional.get().getId());
      libraryModel.setRegistrationDate(libraryModelOptional.get().getRegistrationDate());
      return ResponseEntity.status(HttpStatus.OK).body(libraryService.save(libraryModel));
    }
	
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLibrary(@PathVariable(value = "id") UUID id){
        Optional<LibraryModel> libraryModelOptional = libraryService.findById(id);
        if (!libraryModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
        libraryService.delete(libraryModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully.");
    }
	
}
