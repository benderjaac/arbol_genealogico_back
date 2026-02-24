package com.arbol.services;

import com.arbol.dto.PersonSimpleDto;
import com.arbol.dto.UnionChildCreateDto;
import com.arbol.dto.UnionChildDto;
import com.arbol.dto.UnionDto;
import com.arbol.models.Person;
import com.arbol.models.Union;
import com.arbol.models.UnionChild;
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
public class UnionChildService {

    private final UnionRepository unionRepository;
    private final PersonRepository personRepository;
    private final UnionChildRepository unionChildRepository;

    //CREAR UNIONCHILD
    public UnionChildDto createUnionChild(UnionChildCreateDto request) {

        Person person = personRepository.findById(request.getChildId())
                .orElseThrow(() -> new RuntimeException("Persona (hijo) no encontrada"));

        Union union = unionRepository.findById(request.getUnionId())
                .orElseThrow(() -> new RuntimeException("Union no encontrada"));

        if (person.getId().equals(union.getPerson1().getId()) || person.getId().equals(union.getPerson2().getId())) {
            throw new RuntimeException("Una persona no puede ser su propio hijo");
        }

        if(unionChildRepository.existsByUnionIdAndChildId(union.getId(), person.getId())){
            throw new RuntimeException("La unión con hijo ya existe");
        }

        UnionChild unionChild = new UnionChild();
        unionChild.setChild(person);
        unionChild.setUnion(union);

        return new UnionChildDto(unionChildRepository.save(unionChild));
    }

    //ELIMINAR HIJO-UNION
    public void deleteUnionChild(Long id) {
        unionChildRepository.deleteById(id);
    }

    //EDITAR CHILD-UNION
    public UnionChildDto updateUnionChild(Long id, UnionChildCreateDto request) {
        UnionChild unionChild = unionChildRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no existe Union-Hijo"));

        Person person = personRepository.findById(request.getChildId())
                .orElseThrow(() -> new RuntimeException("Persona (hijo) no encontrada"));

        Union union = unionRepository.findById(request.getUnionId())
                .orElseThrow(() -> new RuntimeException("Union no encontrada"));

        //si es un hijo diferente, buscar que no sea hijo de otra union
        if(!request.getChildId().equals(unionChild.getChild().getId())){
            if(unionChildRepository.existsByChildId(request.getChildId())){
                throw new RuntimeException("El hijo pertenece a otra union");
            }

            if (person.getId().equals(union.getPerson1().getId()) || person.getId().equals(union.getPerson2().getId())) {
                throw new RuntimeException("Una persona no puede ser su propio hijo");
            }
        }

        if(
                request.getChildId().equals(unionChild.getChild().getId()) &&
                request.getUnionId().equals(unionChild.getUnion().getId())
        ){
            throw new RuntimeException("La unión con hijo ya existe");
        }

        unionChild.setChild(person);
        unionChild.setUnion(union);

        return new UnionChildDto(unionChildRepository.save(unionChild));
    }

    //BUSCAR PERSONAS HIJOS DE UNA UNION
    @Transactional(readOnly = true)
    public List<PersonSimpleDto> findChildsByUnion(Long unionId) {
        if (!unionRepository.existsById(unionId)) {
            throw new RuntimeException("Union no encontrada");
        }
        List<UnionChild> relations = unionChildRepository.findByUnionId(unionId);

        return relations.stream()
                .map(UnionChild::getChild)
                .map(PersonSimpleDto::new)
                .collect(Collectors.toList());
    }
}
