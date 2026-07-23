package com.onurtas.archweave.controller.builder;

import com.onurtas.archweave.controller.builder.request.CreateHtmlWidgetRequest;
import com.onurtas.archweave.controller.builder.request.CreatePageRequest;
import com.onurtas.archweave.metadata.page.PageDefinition;
import com.onurtas.archweave.metadata.page.PageDefinitionService;
import com.onurtas.archweave.metadata.page.PageSlugAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/builder/pages")
public class PageBuilderController {

    private final PageDefinitionService pageDefinitionService;

    public PageBuilderController(PageDefinitionService pageDefinitionService) {
        this.pageDefinitionService = pageDefinitionService;
    }

    @GetMapping
    public String listPages(Model model) {
        model.addAttribute("pages", pageDefinitionService.findAll());

        return "builder/page-list";
    }

    @GetMapping("/new")
    public String showCreatePageForm(Model model) {
        model.addAttribute("createPageRequest", new CreatePageRequest("", ""));

        return "builder/create-page";
    }

    @PostMapping
    public String createPage(
            @Valid @ModelAttribute("createPageRequest") CreatePageRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "builder/create-page";
        }

        try {
            PageDefinition page = pageDefinitionService.createPage(
                    request.title(),
                    request.slug()
            );

            return "redirect:/builder/pages/" + page.getSlug();
        } catch (PageSlugAlreadyExistsException exception) {
            bindingResult.rejectValue("slug", "duplicate", exception.getMessage());

            return "builder/create-page";
        }
    }

    @GetMapping("/{slug}")
    public String showPageEditor(@PathVariable String slug, Model model) {
        addPageEditorAttributes(slug, model);

        return "builder/page-editor";
    }

    @PostMapping("/{slug}/widgets/html")
    public String addHtmlWidget(
            @PathVariable String slug,
            @Valid @ModelAttribute("createHtmlWidgetRequest") CreateHtmlWidgetRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            addPageEditorAttributes(slug, model);

            return "builder/page-editor";
        }

        pageDefinitionService.addHtmlWidget(slug, request.html());

        return "redirect:/builder/pages/" + slug;
    }

    private void addPageEditorAttributes(String slug, Model model) {
        model.addAttribute("page", pageDefinitionService.findRequiredBySlug(slug));
        model.addAttribute(
                "createHtmlWidgetRequest",
                new CreateHtmlWidgetRequest("")
        );
    }
}