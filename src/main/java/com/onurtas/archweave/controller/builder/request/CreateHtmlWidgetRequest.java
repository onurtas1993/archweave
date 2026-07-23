package com.onurtas.archweave.controller.builder.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateHtmlWidgetRequest(

        @NotBlank(message = "Widget HTML is required.")
        @Size(max = 50_000, message = "Widget HTML must be at most 50,000 characters.")
        String html
) {
}