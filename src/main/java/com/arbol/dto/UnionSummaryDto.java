package com.arbol.dto;

import com.arbol.models.Person;
import lombok.Getter;

@Getter
public class UnionSummaryDto {

    private Long unionId;

    private PersonSimpleDto spouse;

    private Integer childrenCount;

    public UnionSummaryDto(
            Long unionId,
            Person spouse,
            Integer childrenCount
    ) {
        this.unionId = unionId;
        this.spouse = new PersonSimpleDto(spouse);
        this.childrenCount = childrenCount;
    }

}