package io.bootify.usecase_app.controller;

import io.bootify.usecase_app.domain.User;
import io.bootify.usecase_app.model.UseCaseDTO;
import io.bootify.usecase_app.repos.UserRepository;
import io.bootify.usecase_app.service.UseCaseService;
import io.bootify.usecase_app.util.CustomCollectors;
import io.bootify.usecase_app.util.ReferencedWarning;
import io.bootify.usecase_app.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/useCases")
public class UseCaseController {

    private final UseCaseService useCaseService;
    private final UserRepository userRepository;

    public UseCaseController(final UseCaseService useCaseService,
            final UserRepository userRepository) {
        this.useCaseService = useCaseService;
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("userValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("useCases", useCaseService.findAll());
        return "useCase/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("useCase") final UseCaseDTO useCaseDTO) {
        return "useCase/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("useCase") @Valid final UseCaseDTO useCaseDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "useCase/add";
        }
        useCaseService.create(useCaseDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("useCase.create.success"));
        return "redirect:/useCases";
    }

    @GetMapping("/edit/{useCaseId}")
    public String edit(@PathVariable(name = "useCaseId") final Long useCaseId, final Model model) {
        model.addAttribute("useCase", useCaseService.get(useCaseId));
        return "useCase/edit";
    }

    @PostMapping("/edit/{useCaseId}")
    public String edit(@PathVariable(name = "useCaseId") final Long useCaseId,
            @ModelAttribute("useCase") @Valid final UseCaseDTO useCaseDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "useCase/edit";
        }
        useCaseService.update(useCaseId, useCaseDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("useCase.update.success"));
        return "redirect:/useCases";
    }

    @PostMapping("/delete/{useCaseId}")
    public String delete(@PathVariable(name = "useCaseId") final Long useCaseId,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = useCaseService.getReferencedWarning(useCaseId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            useCaseService.delete(useCaseId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("useCase.delete.success"));
        }
        return "redirect:/useCases";
    }

}
