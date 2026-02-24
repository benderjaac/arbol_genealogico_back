package com.arbol.controllers;

import com.arbol.dto.UnionChildCreateDto;
import com.arbol.dto.UnionChildDto;
import com.arbol.dto.UnionCreateDto;
import com.arbol.dto.UnionDto;
import com.arbol.models.response.HttpOk;
import com.arbol.services.UnionChildService;
import com.arbol.services.UnionService;
import com.arbol.util.Response;
import com.arbol.util.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/child")
@RequiredArgsConstructor
public class UnionChildController {
    private final Response response = new Response(Type.CHILD);
    private final UnionChildService unionChildService;

    // CREAR HIJO-UNION
    @PreAuthorize("hasAuthority('arbol_edit')")
    @PostMapping
    public ResponseEntity<HttpOk> createUnionChild(
            @RequestBody UnionChildCreateDto request
    ) {
        UnionChildDto unionChild = unionChildService.createUnionChild(request);

        return response.create(unionChild.getId().toString(), unionChild);
    }

    // ELIMINAR HIJO-UNION
    @PreAuthorize("hasAuthority('arbol_edit')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> deleteUnionChild(
            @PathVariable Long id
    ) {
        unionChildService.deleteUnionChild(id);
        return response.delete(id.toString());
    }

    // ACTUALIZAR HIJO-UNION
    @PreAuthorize("hasAuthority('arbol_edit')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpOk> updateUnion(
            @PathVariable Long id,
            @RequestBody UnionChildCreateDto request
    ) {
        UnionChildDto unionChild = unionChildService.updateUnionChild(id, request);

        return response.update(unionChild.getId().toString(), unionChild);
    }

    // BUSCAR PERSONAS HIJOS POR UNION
    @PreAuthorize("hasAuthority('arbol_select')")
    @GetMapping("/union/{unionId}")
    public ResponseEntity<HttpOk> findByPerson(
            @PathVariable Long unionId
    ) {
        return response.find(
                unionChildService.findChildsByUnion(unionId)
        );
    }
}
