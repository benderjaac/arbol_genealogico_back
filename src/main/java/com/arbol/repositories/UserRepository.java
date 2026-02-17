package com.arbol.repositories;

import com.arbol.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.perfil p " +
            "JOIN FETCH p.permisos " +
            "WHERE u.username = :username")
    Optional<User> findByUsernameWithPermisos(@Param("username") String username);
}
