package com.arbol.services;

import com.arbol.models.Person;
import com.arbol.models.Photo;
import com.arbol.repositories.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final FileStorageService fileStorageService;
    private final PhotoRepository photoRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public Photo savePhoto(MultipartFile file, Person person){

        UUID photoId = UUID.randomUUID();

        String folderName = buildPersonFolderName(person);

        String uploadDir = this.uploadDir + "/persons/" + folderName;

        String originalName = file.getOriginalFilename();
        String extension = "";

        if(originalName != null && originalName.contains(".")){
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        String fileName = photoId + extension;

        String filePath = fileStorageService.storeFile(file, uploadDir, fileName);

        Photo photo = new Photo();
        photo.setFilePath(filePath);
        photo.setContentType(file.getContentType());
        photo.setPerson(person);
        photo.setId(photoId);

        photo = photoRepository.save(photo);
        return photo;
    }

    public String buildPersonFolderName(Person person) {

        String fullName = (person.getNombre() + "_" +
                person.getApellidoPaterno() + "_" +
                person.getApellidoMaterno())
                .toLowerCase()
                .replace(" ", "_")
                .replaceAll("[^a-z0-9_]", "");

        return person.getId() + "_" + fullName;
    }
}
