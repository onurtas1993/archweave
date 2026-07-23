package com.onurtas.archweave.runtime.page;

import com.onurtas.archweave.metadata.page.PageDefinition;
import com.onurtas.archweave.metadata.page.PageDefinitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PageRuntimeService {

    private final PageDefinitionRepository pageDefinitionRepository;

    public PageRuntimeService(PageDefinitionRepository pageDefinitionRepository) {
        this.pageDefinitionRepository = pageDefinitionRepository;
    }

    public Optional<PageDefinition> findBySlug(String slug) {
        return pageDefinitionRepository.findBySlug(slug);
    }
}