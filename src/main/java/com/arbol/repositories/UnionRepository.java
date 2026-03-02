package com.arbol.repositories;

import com.arbol.dto.UnionSummaryDto;
import com.arbol.models.Union;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UnionRepository extends JpaRepository<Union, Long> {
    @Query("""
        SELECT u FROM Union u
        WHERE (u.person1.id = :p1 AND u.person2.id = :p2)
           OR (u.person1.id = :p2 AND u.person2.id = :p1)
    """)
    Optional<Union> findBetweenPersons(Long p1, Long p2);

    @Query("""
        SELECT u FROM Union u
        WHERE u.person1.id = :personId
           OR u.person2.id = :personId
    """)
    List<Union> findByPerson(Long personId);

    @Query("""
    SELECT new com.arbol.dto.UnionSummaryDto(
        u.id,
        u.person2,
        SIZE(u.children)
    )
    FROM Union u
    WHERE u.person1.id = :personId
""")
    List<UnionSummaryDto> findUnionsWherePerson1(@Param("personId") Long personId);


    @Query("""
    SELECT new com.arbol.dto.UnionSummaryDto(
        u.id,
        u.person1,
        SIZE(u.children)
    )
    FROM Union u
    WHERE u.person2.id = :personId
""")
    List<UnionSummaryDto> findUnionsWherePerson2(@Param("personId") Long personId);
}
