package com.onurtas.archweave.metadata.page;

import com.onurtas.archweave.metadata.widget.WidgetDefinition;
import com.onurtas.archweave.metadata.widget.WidgetType;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class PageDefinitionService {

    private final PageDefinitionRepository pageDefinitionRepository;

    public PageDefinitionService(PageDefinitionRepository pageDefinitionRepository) {
        this.pageDefinitionRepository = pageDefinitionRepository;
    }

    @Transactional(readOnly = true)
    public List<PageDefinition> findAll() {
        return pageDefinitionRepository.findAll(Sort.by("title").ascending());
    }

    @Transactional(readOnly = true)
    public PageDefinition findRequiredBySlug(String slug) {
        return pageDefinitionRepository.findBySlug(slug)
                .orElseThrow(() -> new PageDefinitionNotFoundException(slug));
    }

    public PageDefinition createPage(String title, String slug) {
        String normalizedTitle = title.trim();
        String normalizedSlug = normalizeSlug(slug);

        if (pageDefinitionRepository.findBySlug(normalizedSlug).isPresent()) {
            throw new PageSlugAlreadyExistsException(normalizedSlug);
        }

        PageDefinition page = new PageDefinition(normalizedSlug, normalizedTitle);

        return pageDefinitionRepository.save(page);
    }

    public PageDefinition addHtmlWidget(String pageSlug, String html) {
        PageDefinition page = findRequiredBySlug(pageSlug);

        int displayOrder = page.getWidgets().size() + 1;
        WidgetDefinition widget = new WidgetDefinition(
                WidgetType.HTML,
                html.trim(),
                displayOrder
        );

        page.addWidget(widget);

        return pageDefinitionRepository.save(page);
    }

    private String normalizeSlug(String slug) {
        String normalizedSlug = slug.trim().toLowerCase(Locale.ROOT);

        if (!normalizedSlug.matches("[a-z0-9]+(?:-[a-z0-9]+)*")) {
            throw new IllegalArgumentException(
                    "Slug must use lowercase letters, numbers, and hyphens only."
            );
        }

        return normalizedSlug;
    }
}