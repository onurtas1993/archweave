package com.onurtas.archweave.controller.page;

import com.onurtas.archweave.metadata.page.PageDefinition;
import com.onurtas.archweave.runtime.page.PageRuntimeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/pages")
public class PageController {

    private final PageRuntimeService pageRuntimeService;

    public PageController(PageRuntimeService pageRuntimeService) {
        this.pageRuntimeService = pageRuntimeService;
    }

    @GetMapping("/{slug}")
    public String showPage(@PathVariable String slug, Model model) {
        PageDefinition page = pageRuntimeService.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Page not found: " + slug
                ));

        model.addAttribute("page", page);

        return "page";
    }
}