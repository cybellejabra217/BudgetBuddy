package org.example.apibudgetbuddyproject.repository;

import org.example.apibudgetbuddyproject.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing sources.
 */
@Repository
public interface SourceRepository extends JpaRepository<Source, Integer> {

    // finds source by its name
    Optional<Source> findByName(String name);

}