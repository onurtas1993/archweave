package com.onurtas.archweave.metadata.page;

import com.onurtas.archweave.metadata.widget.WidgetDefinition;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "page_definitions")
public class PageDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(nullable = false, length = 200)
    private String title;

    @OneToMany(
            mappedBy = "page",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("displayOrder ASC")
    private List<WidgetDefinition> widgets = new ArrayList<>();

    protected PageDefinition() {
        // Required by JPA.
    }

    public PageDefinition(String slug, String title) {
        this.slug = slug;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public List<WidgetDefinition> getWidgets() {
        return List.copyOf(widgets);
    }

    public void addWidget(WidgetDefinition widget) {
        widgets.add(widget);
        widget.setPage(this);
    }
}