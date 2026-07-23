package com.onurtas.archweave.metadata.page;

public class PageDefinitionNotFoundException extends RuntimeException {

    public PageDefinitionNotFoundException(String slug) {
        super("Page definition not found: " + slug);
    }
}