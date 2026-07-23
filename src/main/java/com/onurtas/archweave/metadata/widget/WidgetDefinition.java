package com.onurtas.archweave.metadata.widget;

import com.onurtas.archweave.metadata.page.PageDefinition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "widget_definitions")
public class WidgetDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "page_id", nullable = false)
    private PageDefinition page;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private WidgetType type;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int displayOrder;

    protected WidgetDefinition() {
        // Required by JPA.
    }

    public WidgetDefinition(WidgetType type, String content, int displayOrder) {
        this.type = type;
        this.content = content;
        this.displayOrder = displayOrder;
    }

    public Long getId() {
        return id;
    }

    public WidgetType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public PageDefinition getPage() {
        return page;
    }

    public void setPage(PageDefinition page) {
        this.page = page;
    }
}