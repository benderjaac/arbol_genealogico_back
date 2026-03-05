package com.arbol.controllers;

import com.arbol.models.Photo;
import com.arbol.repositories.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoRepository photoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getPhoto(@PathVariable UUID id) throws IOException {

        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foto no encontrada"));

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
