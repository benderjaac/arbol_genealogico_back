package com.arbol.services;

import com.arbol.exceptions.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    public String storeFile(MultipartFile file, String fileName, Long personId) {

        try {
            String uploadDir = "uploads/persons/" + personId;

            Path path = Paths.get(uploadDir);
            Files.createDirectories(path);

            Path filePath = path.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString();

        } catch (IOException e) {
            throw new FileStorageException("Error storing file", e);
        }
    }
}
