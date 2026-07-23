package com.onurtas.archweave.controller.builder.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreatePageRequest(

        @NotBlank(message = "Page title is required.")
        @Size(max = 200, message = "Page title must be at most 200 characters.")
        String title,

        @NotBlank(message = "Page slug is required.")
        @Size(max = 100, message = "Page slug must be at most 100 characters.")
        @Pattern(
                regexp = "[a-z0-9]+(?:-[a-z0-9]+)*",
                message = "Slug may contain lowercase letters, numbers, and hyphens only."
        )
        String slug
) {
}