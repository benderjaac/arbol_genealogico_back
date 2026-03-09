package com.arbol.services;

import com.arbol.dto.UnionCreateDto;
import com.arbol.dto.UnionDto;
import com.arbol.dto.UnionSummaryDto;
import com.arbol.models.Person;
import com.arbol.models.Union;
import com.arbol.models.UnionChild;
import com.arbol.repositories.PersonRepository;
import com.arbol.repositories.UnionChildRepository;
import com.arbol.repositories.UnionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    //CREAR UNION
    public UnionDto createUnion(UnionCreateDto request) {

        Person p1 = personRepository.findById(request.getPerson1Id())
                .orElseThrow(() -> new RuntimeException("Person1 no encontrada"));

        Person p2;

        if (request.getPerson2Id() == null) {
            String genero = p1.getGenero().equals("m")?"f":"m";
            p2 = personService.createPlaceholder(genero);
        } else {
            p2 = personRepository.findById(request.getPerson2Id())
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
        union.setFechaInicio(request.getFechaInicio());
        union.setFechaFin(request.getFechaFin());
        union.setTipo(request.getTipo());

        return new UnionDto(unionRepository.save(union));
    }

    //ELIMINAR UNION (si no existen hijos)
    public void deleteUnion(Long unionId) {

        if (unionChildRepository.existsByUnionId(unionId)) {
            throw new RuntimeException("No se puede eliminar una unión con hijos");
        }

        unionRepository.deleteById(unionId);
    }

    //EDITAR UNION (sin cambiar padres si hay hijos)
    public UnionDto updateUnion(Long unionId, UnionCreateDto request) {
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

    //BUSCAR UNIONES DE UNA PERSONA (por alguna persona, person1 o person2)
    @Transactional(readOnly = true)
    public List<UnionSummaryDto> findUnionsByPerson(Long personId) {
        List<Union> unions1 = unionRepository.findUnionsWherePerson1(personId);
        List<Union> unions2 = unionRepository.findUnionsWherePerson2(personId);

        List<UnionSummaryDto> result = new ArrayList<>();

        for (Union u : unions1) {

            List<Person> children = u.getChildren()
                    .stream()
                    .map(UnionChild::getChild)
                    .toList();

            result.add(new UnionSummaryDto(
                    u.getId(),
                    u.getPerson2(),
                    children
            ));
        }

        for (Union u : unions2) {

            List<Person> children = u.getChildren()
                    .stream()
                    .map(UnionChild::getChild)
                    .toList();

            result.add(new UnionSummaryDto(
                    u.getId(),
                    u.getPerson1(),
                    children
            ));
        }

        return result;

    }
}
