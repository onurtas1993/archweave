package com.onurtas.archweave.metadata.page;

public class PageSlugAlreadyExistsException extends RuntimeException {

    public PageSlugAlreadyExistsException(String slug) {
        super("A page definition already uses the slug: " + slug);
    }
}