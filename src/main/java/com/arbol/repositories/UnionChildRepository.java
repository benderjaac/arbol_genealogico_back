package com.arbol.repositories;

import com.arbol.models.Person;
import com.arbol.models.Union;
import com.arbol.models.UnionChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnionChildRepository extends JpaRepository<UnionChild, Long> {

        boolean existsByUnionId(Long unionId);

        boolean existsByChildId(Long childId);

        boolean existsByUnionIdAndChildId(Long unionId, Long childId);

        List<UnionChild> findByUnionId(Long unionId);
}
