package com.arbol.dto;

import com.arbol.models.Person;
import lombok.Getter;

import java.util.List;

@Getter
public class UnionSummaryDto {

    private Long unionId;

    private PersonSimpleDto spouse;

    private List<PersonSimpleDto> children;

    public UnionSummaryDto(
            Long unionId,
            Person spouse,
            List<Person> children
    ) {
        this.unionId = unionId;
        this.spouse = new PersonSimpleDto(spouse);
        this.children = children
                .stream()
                .map(PersonSimpleDto::new)
                .toList();
    }

}