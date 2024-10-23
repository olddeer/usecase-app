package io.bootify.usecase_app.controller;

import io.bootify.usecase_app.domain.UseCase;
import io.bootify.usecase_app.model.UserRecommendationDTO;
import io.bootify.usecase_app.repos.UseCaseRepository;
import io.bootify.usecase_app.service.UserRecommendationService;
import io.bootify.usecase_app.util.CustomCollectors;
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
@RequestMapping("/userRecommendations")
public class UserRecommendationController {

    private final UserRecommendationService userRecommendationService;
    private final UseCaseRepository useCaseRepository;

    public UserRecommendationController(final UserRecommendationService userRecommendationService,
            final UseCaseRepository useCaseRepository) {
        this.userRecommendationService = userRecommendationService;
        this.useCaseRepository = useCaseRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("useCaseValues", useCaseRepository.findAll(Sort.by("useCaseId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(UseCase::getUseCaseId, UseCase::getUseCaseId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("userRecommendations", userRecommendationService.findAll());
        return "userRecommendation/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("userRecommendation") final UserRecommendationDTO userRecommendationDTO) {
        return "userRecommendation/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("userRecommendation") @Valid final UserRecommendationDTO userRecommendationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "userRecommendation/add";
        }
        userRecommendationService.create(userRecommendationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("userRecommendation.create.success"));
        return "redirect:/userRecommendations";
    }

    @GetMapping("/edit/{recommendationId}")
    public String edit(@PathVariable(name = "recommendationId") final Long recommendationId,
            final Model model) {
        model.addAttribute("userRecommendation", userRecommendationService.get(recommendationId));
        return "userRecommendation/edit";
    }

    @PostMapping("/edit/{recommendationId}")
    public String edit(@PathVariable(name = "recommendationId") final Long recommendationId,
            @ModelAttribute("userRecommendation") @Valid final UserRecommendationDTO userRecommendationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "userRecommendation/edit";
        }
        userRecommendationService.update(recommendationId, userRecommendationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("userRecommendation.update.success"));
        return "redirect:/userRecommendations";
    }

    @PostMapping("/delete/{recommendationId}")
    public String delete(@PathVariable(name = "recommendationId") final Long recommendationId,
            final RedirectAttributes redirectAttributes) {
        userRecommendationService.delete(recommendationId);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("userRecommendation.delete.success"));
        return "redirect:/userRecommendations";
    }

}
