package com.arbol.services;

import com.arbol.dto.PersonSimpleDto;
import com.arbol.models.Person;
import com.arbol.models.User;
import com.arbol.models.db.Query;
import com.arbol.models.db.Result;
import com.arbol.repositories.DBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private DBRepository db;

    public Result<PersonSimpleDto> findAllSimple(Query query){
        Result<Person> result = db.findAll(Person.class, query, false);
        List<PersonSimpleDto> dtoList = result.getData().stream()
                .map(PersonSimpleDto::new)
                .collect(Collectors.toList());
        return new Result<>(dtoList, result.getPagination());
    }
}
