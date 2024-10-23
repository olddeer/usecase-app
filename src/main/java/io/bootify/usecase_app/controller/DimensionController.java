package io.bootify.usecase_app.controller;

import io.bootify.usecase_app.model.DimensionDTO;
import io.bootify.usecase_app.service.DimensionService;
import io.bootify.usecase_app.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/dimensions")
public class DimensionController {

    private final DimensionService dimensionService;

    public DimensionController(final DimensionService dimensionService) {
        this.dimensionService = dimensionService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("dimensions", dimensionService.findAll());
        return "dimension/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("dimension") final DimensionDTO dimensionDTO) {
        return "dimension/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("dimension") @Valid final DimensionDTO dimensionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dimension/add";
        }
        dimensionService.create(dimensionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("dimension.create.success"));
        return "redirect:/dimensions";
    }

    @GetMapping("/edit/{dimensionId}")
    public String edit(@PathVariable(name = "dimensionId") final Long dimensionId,
            final Model model) {
        model.addAttribute("dimension", dimensionService.get(dimensionId));
        return "dimension/edit";
    }

    @PostMapping("/edit/{dimensionId}")
    public String edit(@PathVariable(name = "dimensionId") final Long dimensionId,
            @ModelAttribute("dimension") @Valid final DimensionDTO dimensionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dimension/edit";
        }
        dimensionService.update(dimensionId, dimensionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("dimension.update.success"));
        return "redirect:/dimensions";
    }

    @PostMapping("/delete/{dimensionId}")
    public String delete(@PathVariable(name = "dimensionId") final Long dimensionId,
            final RedirectAttributes redirectAttributes) {
        dimensionService.delete(dimensionId);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("dimension.delete.success"));
        return "redirect:/dimensions";
    }

}
