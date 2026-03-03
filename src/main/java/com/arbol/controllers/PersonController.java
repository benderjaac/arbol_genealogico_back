package com.arbol.controllers;

import com.arbol.dto.PersonCreateDto;
import com.arbol.dto.PersonSimpleDto;
import com.arbol.models.Photo;
import com.arbol.models.db.Query;
import com.arbol.models.response.HttpOk;
import com.arbol.repositories.PhotoRepository;
import com.arbol.services.PersonService;
import com.arbol.util.Response;
import com.arbol.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.concurrent.TimeUnit;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private Response response = new Response(Type.PERSON);
    private final PhotoRepository photoRepository;

    private final PersonService personService;

    @PreAuthorize("hasAuthority('arbol_select')")
    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request,
            @RequestBody Query query
    ) {
        return response.find(personService.findAllSimple(query));
    }

    //CREAR PERSONA SIMPLE
    @PreAuthorize("hasAuthority('arbol_edit')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpOk> createPerson(
            @RequestPart("person") PersonCreateDto dto,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        PersonSimpleDto person = personService.createPerson(dto, photo);

        return response.create(person.getId().toString(), person);
    }

    // ELIMINAR PERSONA
    @PreAuthorize("hasAuthority('arbol_edit')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> deletePerson(
            @PathVariable Long id
    ) {
        personService.deletePerson(id);
        return response.delete(id.toString());
    }

    // ACTUALIZAR PERSONA
    @PreAuthorize("hasAuthority('arbol_edit')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpOk> updatePerson(
            @PathVariable Long id,
            @Valid @RequestBody PersonCreateDto request
    ) {
        PersonSimpleDto person = personService.updatePerson(id, request);

        return response.update(person.getId().toString(), person);
    }

    //PHOTOS
    @GetMapping("/photos/{photoId}")
    public ResponseEntity<Resource> getPhoto(@PathVariable Long photoId) throws IOException {

        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));

        Path path = Paths.get(photo.getFilePath());

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found");
        }
        if (!resource.isReadable()) {
            throw new RuntimeException("File not readable");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(photo.getContentType()))
                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .body(resource);
    }
}
