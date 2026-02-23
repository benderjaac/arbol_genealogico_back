package com.arbol.services;

import com.arbol.dto.CreateUnionRequestDto;
import com.arbol.dto.PersonSimpleDto;
import com.arbol.dto.UnionDto;
import com.arbol.models.Person;
import com.arbol.models.Union;
import com.arbol.models.db.Query;
import com.arbol.models.db.Result;
import com.arbol.repositories.PersonRepository;
import com.arbol.repositories.UnionChildRepository;
import com.arbol.repositories.UnionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UnionService {

    private final UnionRepository unionRepository;
    private final PersonRepository personRepository;
    private final PersonService personService;
    private final UnionChildRepository unionChildRepository;

    //Crear union
    public UnionDto createUnion(CreateUnionRequestDto requets) {

        Person p1 = personRepository.findById(requets.getPerson1Id())
                .orElseThrow(() -> new RuntimeException("Person1 no encontrada"));

        Person p2;

        if (requets.getPerson2Id() == null) {
            String genero = p1.getGenero().equals("m")?"f":"m";
            p2 = personService.createPlaceholder(genero);
        } else {
            p2 = personRepository.findById(requets.getPerson2Id())
                    .orElseThrow(() -> new RuntimeException("Person2 no encontrada"));
        }

        if (p1.getId().equals(p2.getId())) {
            throw new RuntimeException("Una persona no puede unirse consigo misma");
        }

        unionRepository.findBetweenPersons(p1.getId(), p2.getId())
                .ifPresent(u -> {
                    throw new RuntimeException("La unión ya existe");
                });

        Union union = new Union();
        union.setPerson1(p1);
        union.setPerson2(p2);
        union.setFechaInicio(requets.getFechaInicio());
        union.setFechaFin(requets.getFechaFin());
        union.setTipo(requets.getTipo());

        return new UnionDto(unionRepository.save(union));
    }

    //Eliminar union (si no existen hijos)
    public void deleteUnion(Long unionId) {

        if (unionChildRepository.existsByUnionId(unionId)) {
            throw new RuntimeException("No se puede eliminar una unión con hijos");
        }

        unionRepository.deleteById(unionId);
    }

    //Editar union (sin cambiar padres si hay hijos)
    public UnionDto updateUnion(Long unionId, CreateUnionRequestDto request) {
        Union union = unionRepository.findById(unionId)
                .orElseThrow(() -> new RuntimeException("Unión no encontrada"));

        if (unionChildRepository.existsByUnionId(unionId)) {
            throw new RuntimeException("No se pueden cambiar la unión, ya tiene hijos");
        }

        Person p1 = personRepository.findById(request.getPerson1Id())
                .orElseThrow(() -> new RuntimeException("Person1 no encontrada"));

        Person p2 = personRepository.findById(request.getPerson2Id())
                .orElseThrow(() -> new RuntimeException("Person2 no encontrada"));

        union.setPerson1(p1);
        union.setPerson2(p2);
        union.setFechaInicio(request.getFechaInicio());
        union.setFechaFin(request.getFechaFin());
        union.setTipo(request.getTipo());

        return new UnionDto(unionRepository.save(union));
    }

    //Seleccionar uniones (por alguna persona, person1 o person2)
    @Transactional(readOnly = true)
    public List<UnionDto> findUnionsByPerson(Long personId) {
        List<Union> result = unionRepository.findByPerson(personId);
        List<UnionDto> dtoList = result.stream()
                .map(UnionDto::new)
                .collect(Collectors.toList());
        return dtoList;
    }
}
