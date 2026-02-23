package com.arbol.controllers;

import com.arbol.models.db.Query;
import com.arbol.models.response.HttpOk;
import com.arbol.services.PersonService;
import com.arbol.util.Response;
import com.arbol.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private Response response = new Response(Type.USUARIO);

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
}
