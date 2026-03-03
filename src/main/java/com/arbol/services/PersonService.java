package com.arbol.services;

import com.arbol.dto.PersonCreateDto;
import com.arbol.dto.PersonSimpleDto;
import com.arbol.exceptions.FileStorageException;
import com.arbol.models.Person;
import com.arbol.models.Photo;
import com.arbol.models.db.Query;
import com.arbol.models.db.Result;
import com.arbol.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {

    private final DBRepository db;
    private final PersonRepository personRepository;
    private final UnionRepository unionRepository;
    private final UnionChildRepository unionChildRepository;
    private final PhotoRepository photoRepository;
    private final FileStorageService fileStorageService;

    public Result<PersonSimpleDto> findAllSimple(Query query){
        Result<Person> result = db.findAll(Person.class, query, false);
        List<PersonSimpleDto> dtoList = result.getData().stream()
                .map(PersonSimpleDto::new)
                .collect(Collectors.toList());
        return new Result<>(dtoList, result.getPagination());
    }

    //CREAR PLACEHOLDER
    public Person createPlaceholder(String genero) {
        Person p = new Person();
        p.setNombre("Progenitor desconocido");
        p.setGenero(genero);
        p.setPlaceholder(true);
        return personRepository.save(p);
    }

    //CREAR PERSONA
    public PersonSimpleDto createPerson(PersonCreateDto dto, MultipartFile file){

        Person person = new Person();
        mapDtoToEntity(dto, person);
        person.setPlaceholder(false);

        personRepository.save(person);

        if (file != null && !file.isEmpty()) {

            String filePath = fileStorageService.storeFile(file, person.getId());

            Photo photo = new Photo();
            photo.setFileName(file.getOriginalFilename());
            photo.setFilePath(filePath.toString());
            photo.setContentType(file.getContentType());
            photo.setSize(file.getSize());
            photo.setPerson(person);

            photoRepository.save(photo);

            person.setMainPhoto(photo);
        }

        return new PersonSimpleDto(person);
    }

    //ELIMINAR PERSONA
    public void deletePerson(Long id){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        // Validar que no tenga uniones
        boolean hasUnions = !unionRepository.findByPerson(id).isEmpty();
        if (hasUnions) {
            throw new RuntimeException("No se puede eliminar persona con uniones");
        }

        // Validar que no sea hijo en alguna union
        boolean isChild = unionChildRepository.existsByChildId(id);
        if (isChild) {
            throw new RuntimeException("No se puede eliminar persona que es hijo en una unión");
        }

        personRepository.delete(person);
    }

    //EDITAR PERSONA
    public PersonSimpleDto updatePerson(Long id, PersonCreateDto dto, MultipartFile file){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        mapDtoToEntity(dto, person);

        if (file != null && !file.isEmpty()) {

            // OPCIÓN SIMPLE: crear nueva foto y marcarla como principal
            String filePath = fileStorageService.storeFile(file, person.getId());

            Photo photo = new Photo();
            photo.setFileName(file.getOriginalFilename());
            photo.setFilePath(filePath);
            photo.setContentType(file.getContentType());
            photo.setSize(file.getSize());
            photo.setPerson(person);

            photoRepository.save(photo);

            person.setMainPhoto(photo);
        }

        personRepository.save(person);

        return new PersonSimpleDto(person);
    }

    private void mapDtoToEntity(PersonCreateDto dto, Person person) {
        person.setNombre(dto.getNombre());
        person.setApellidoPaterno(dto.getApellidoPaterno());
        person.setApellidoMaterno(dto.getApellidoMaterno());
        person.setFechaNacimiento(dto.getFechaNacimiento());
        person.setGenero(dto.getGenero());
        person.setLugarNacimiento(dto.getLugarNacimiento());
        person.setNotas(dto.getNotas());
    }
}
