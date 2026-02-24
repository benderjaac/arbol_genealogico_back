package com.arbol.controllers;

import com.arbol.dto.UnionCreateDto;
import com.arbol.dto.UnionDto;
import com.arbol.models.response.HttpOk;
import com.arbol.services.UnionService;
import com.arbol.util.Response;
import com.arbol.util.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/unions")
@RequiredArgsConstructor
public class UnionController {
    private final Response response = new Response(Type.UNION);
    private final UnionService unionService;

    // CREAR UNION
    @PreAuthorize("hasAuthority('arbol_edit')")
    @PostMapping
    public ResponseEntity<HttpOk> createUnion(
            @RequestBody UnionCreateDto request
    ) {
        UnionDto union = unionService.createUnion(request);

        return response.create(union.getId().toString(), union);
    }

    // ELIMINAR UNION
    @PreAuthorize("hasAuthority('arbol_edit')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> deleteUnion(
            @PathVariable Long id
    ) {
        unionService.deleteUnion(id);
        return response.delete(id.toString());
    }

    // ACTUALIZAR UNION
    @PreAuthorize("hasAuthority('arbol_edit')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpOk> updateUnion(
            @PathVariable Long id,
            @RequestBody UnionCreateDto request
    ) {
        UnionDto union = unionService.updateUnion(id, request);

        return response.update(union.getId().toString(), union);
    }

    // BUSCAR POR PERSONA
    @PreAuthorize("hasAuthority('arbol_select')")
    @GetMapping("/person/{personId}")
    public ResponseEntity<HttpOk> findByPerson(
            @PathVariable Long personId
    ) {
        return response.find(
                unionService.findUnionsByPerson(personId)
        );
    }
}
