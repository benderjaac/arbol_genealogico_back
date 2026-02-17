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
public class Filter {

    @NotEmpty
    private String field;

    @NotEmpty
    private String operator;

    @NotEmpty
    private String value;

    @NotEmpty
    private String type;

    @Override
    public String toString() {
        return "Filter [field='" + field + "', operator='" + operator + "', value='" + value + "', type='" + type + "']";
    }

}