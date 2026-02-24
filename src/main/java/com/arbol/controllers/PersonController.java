package com.arbol.controllers;

import com.arbol.dto.PersonCreateDto;
import com.arbol.dto.PersonSimpleDto;
import com.arbol.models.db.Query;
import com.arbol.models.response.HttpOk;
import com.arbol.services.PersonService;
import com.arbol.util.Response;
import com.arbol.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private Response response = new Response(Type.PERSON);

    @Autowired
    private PersonService personService;

    @PreAuthorize("hasAuthority('arbol_select')")
    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request,
            @RequestBody Query query
    ) {
        return response.find(personService.findAllSimple(query));
    }

    //CREAR PERSONA SIMPLE
    @PreAuthorize("hasAuthority('arbol_edit')")
    @PostMapping
    public ResponseEntity<HttpOk> createPerson(
            @RequestBody PersonCreateDto request
    ) {
        PersonSimpleDto person = personService.createPerson(request);

        return response.create(person.getId().toString(), person);
    }

    // ELIMINAR PERSONA
    @PreAuthorize("hasAuthority('arbol_edit')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> deletePerson(
            @PathVariable Long id
    ) {
        personService.deletePerson(id);
        return response.delete(id.toString());
    }

    // ACTUALIZAR PERSONA
    @PreAuthorize("hasAuthority('arbol_edit')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpOk> updatePerson(
            @PathVariable Long id,
            @RequestBody PersonCreateDto request
    ) {
        PersonSimpleDto person = personService.updatePerson(id, request);

        return response.update(person.getId().toString(), person);
    }
}
