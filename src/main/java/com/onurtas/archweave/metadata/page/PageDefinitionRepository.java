package com.onurtas.archweave.metadata.page;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageDefinitionRepository extends JpaRepository<PageDefinition, Long> {

    @EntityGraph(attributePaths = "widgets")
    Optional<PageDefinition> findBySlug(String slug);
}