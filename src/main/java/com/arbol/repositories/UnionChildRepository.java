package com.arbol.repositories;

import com.arbol.models.UnionChild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnionChildRepository extends JpaRepository<UnionChild, Long> {
        boolean existsByUnionId(Long unionId);

        boolean existsByChildId(Long childId);
}
