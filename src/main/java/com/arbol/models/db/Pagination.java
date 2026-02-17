package com.arbol.models.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {

    @NotEmpty
    private Integer currentPage = 1;

    @NotEmpty
    private Integer perPage = 10;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer prevPage;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer nextPage;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long totalItems = 0L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer totalPages = 0;
}
