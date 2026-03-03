package com.arbol.repositories;

import com.arbol.models.Photo;
import com.arbol.models.UnionChild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
