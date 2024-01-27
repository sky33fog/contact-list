package com.example.contactlist;

import com.example.contactlist.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        return "index";
    }

    @GetMapping("/contact/update/{id}")
    public String showUpdateForm(@PathVariable UUID id, Model model) {
        Contact contact = contactService.findById(id);
        if(contact != null) {
            model.addAttribute("contact", contact);
            return "update";
        }
        return "redirect:/";
    }

    @PostMapping("/contact/update")
    public String updateContacts(@ModelAttribute Contact contact) {
        contactService.update(contact);
        return "redirect:/";
    }

    @GetMapping("/contact/save")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "update";
    }

    @GetMapping("/contact/delete/{id}")
    public String delete(@PathVariable UUID id) {
        contactService.delete(id);
        return "redirect:/";
    }
}