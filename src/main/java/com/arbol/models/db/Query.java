package com.arbol.models.db;

import com.arbol.exceptions.AppException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class Query {

    @Valid
    private List<Filter> filters = new ArrayList<>();

    @Valid
    private List<Sort> sorter = new ArrayList<>();

    @Valid
    private Pagination pagination = new Pagination();

    private List<String> fetchRelations = new ArrayList<>();

    public void addFetch(String relation) {
        if (!fetchRelations.contains(relation)) {
            fetchRelations.add(relation);
        }
    }

    public Query(String key, String operator, String value) {
        addFilter(key, operator, value, "string");
    }

    public Query(String key, String operator, Integer value) {
        addFilter(key, operator, value + "", "number");
    }

    public void addFilter(String key, String operator, String value, String type) {
        this.filters.add(new Filter(key, operator, value, type));
    }

    public void addFilter(String key, String operator, String value) {
        this.filters.add(new Filter(key, operator, value, "string"));
    }

    public void addFilter(String key, String operator, Integer value) {
        this.filters.add(new Filter(key, operator, value + "", "number"));
    }

    public void addFilter(String key, Boolean value) {
        this.filters.add(new Filter(key, "=", value + "", "boolean"));
    }

    public void addFilterIsNull(String key, Boolean value) {
        this.filters.add(new Filter(key, "=", value + "", "isnull"));
    }

    public void addFilterIn(String key, List<Integer> values) {
        String valuesJoin = "[" + values.stream().map(String::valueOf).collect(Collectors.joining(",")) + "]";
        this.filters.add(new Filter(key, "in", valuesJoin, "list"));
    }

    public void addSortAsc(String field) {
        this.sorter.add(new Sort(field, "ASC"));
    }

    public void addSortDesc(String field) {
        this.sorter.add(new Sort(field, "DESC"));
    }

    public void setDisabledPagination(boolean disabled) {
        this.getPagination().setPerPage(disabled ? -1 : 10);
        this.getPagination().setCurrentPage(1);
    }

    public boolean isDisabledPagination() {
        return this.getPagination().getPerPage() == -1;
    }

    /***
     * Utilizada en reportes
     * @param f (filter)
     */
    public void setF(String f) {
        if (!f.isEmpty()) {
            try {
                List<Filter> list = Arrays.asList(new ObjectMapper().readValue(f, Filter[].class));
                filters.addAll(list);
            } catch (Exception e) {
                throw new AppException(e.getMessage());
            }
        }
    }

    /**
     * Utilizada en reportes
     *
     * @param s (sorter)
     */
    public void setS(String s) {
        if (!s.isEmpty()) {
            try {
                List<Sort> list = Arrays.asList(new ObjectMapper().readValue(s, Sort[].class));
                sorter.addAll(list);
            } catch (Exception e) {
                throw new AppException(e.getMessage());
            }
        }
    }

    /**
     * Utilizada en reportes
     *
     * @param p (pagination)
     */
    public void setP(String p) {
        if (!p.isEmpty()) {
            try {
                pagination = new ObjectMapper().readValue(p, Pagination.class);
            } catch (Exception e) {
                throw new AppException(e.getMessage());
            }
        }
    }

}