package com.arbol.repositories;

import com.arbol.models.Person;
import com.arbol.models.Union;
import com.arbol.models.UnionChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UnionChildRepository extends JpaRepository<UnionChild, Long> {

        boolean existsByUnionId(Long unionId);

        boolean existsByChildId(Long childId);

        boolean existsByUnionIdAndChildId(Long unionId, Long childId);

        List<UnionChild> findByUnionId(Long unionId);

        void deleteByUnionIdAndChildId(Long unionId, Long childId);

        @Query("""
                    SELECT uc FROM UnionChild uc
                    JOIN FETCH uc.union u
                    JOIN FETCH u.person1
                    JOIN FETCH u.person2
                    WHERE uc.child.id = :personId
                """)
        Optional<UnionChild> findParentUnion(Long personId);
}
