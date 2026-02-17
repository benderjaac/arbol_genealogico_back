package com.arbol.models.db;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sort {

    @NotEmpty
    private String field;

    @NotEmpty
    private String order;

    @Override
    public String toString() {
        return "Order [field='" + field + "', order='" + order + "']";
    }

}