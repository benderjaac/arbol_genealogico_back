package com.arbol.services;

import com.arbol.dto.PersonCreateDto;
import com.arbol.dto.PersonSimpleDto;
import com.arbol.models.Person;
import com.arbol.models.db.Query;
import com.arbol.models.db.Result;
import com.arbol.repositories.DBRepository;
import com.arbol.repositories.PersonRepository;
import com.arbol.repositories.UnionChildRepository;
import com.arbol.repositories.UnionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {

    private final DBRepository db;
    private final PersonRepository personRepository;
    private final UnionRepository unionRepository;
    private final UnionChildRepository unionChildRepository;

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
        p.setFoto("default.png");
        p.setPlaceholder(true);
        return personRepository.save(p);
    }

    //CREAR PERSONA
    public PersonSimpleDto createPerson(PersonCreateDto dto){
        validateCreate(dto);

        Person person = new Person();
        mapDtoToEntity(dto, person);
        person.setPlaceholder(false);

        personRepository.save(person);

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
    public PersonSimpleDto updatePerson(Long id, PersonCreateDto dto){
        if (id == null) {
            throw new RuntimeException("Id requerido para actualizar");
        }

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        mapDtoToEntity(dto, person);

        personRepository.save(person);

        return new PersonSimpleDto(person);
    }

    //oTRAS FUNCIONES
    private void validateCreate(PersonCreateDto dto) {

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new RuntimeException("Nombre es obligatorio");
        }

        if (dto.getGenero() == null || dto.getGenero().isBlank()) {
            throw new RuntimeException("Genero es obligatorio");
        }

        if (dto.getFoto() == null || dto.getFoto().isBlank()) {
            throw new RuntimeException("Foto es obligatoria");
        }
    }

    private void mapDtoToEntity(PersonCreateDto dto, Person person) {
        person.setNombre(dto.getNombre());
        person.setApellidoPaterno(dto.getApellidoPaterno());
        person.setApellidoMaterno(dto.getApellidoMaterno());
        person.setFechaNacimiento(dto.getFechaNacimiento());
        person.setGenero(dto.getGenero());
        person.setFoto(dto.getFoto());
        person.setLugarNacimiento(dto.getLugarNacimiento());
        person.setNotas(dto.getNotas());
    }
}
