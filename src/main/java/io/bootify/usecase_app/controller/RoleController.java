package io.bootify.usecase_app.controller;

import io.bootify.usecase_app.model.RoleDTO;
import io.bootify.usecase_app.service.RoleService;
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
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "role/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("role") final RoleDTO roleDTO) {
        return "role/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("role") @Valid final RoleDTO roleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "role/add";
        }
        roleService.create(roleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("role.create.success"));
        return "redirect:/roles";
    }

    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable(name = "roleId") final Long roleId, final Model model) {
        model.addAttribute("role", roleService.get(roleId));
        return "role/edit";
    }

    @PostMapping("/edit/{roleId}")
    public String edit(@PathVariable(name = "roleId") final Long roleId,
            @ModelAttribute("role") @Valid final RoleDTO roleDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "role/edit";
        }
        roleService.update(roleId, roleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("role.update.success"));
        return "redirect:/roles";
    }

    @PostMapping("/delete/{roleId}")
    public String delete(@PathVariable(name = "roleId") final Long roleId,
            final RedirectAttributes redirectAttributes) {
        roleService.delete(roleId);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("role.delete.success"));
        return "redirect:/roles";
    }

}
