package com.example.contactlist.service;

import com.example.contactlist.Contact;
import com.example.contactlist.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public Contact findById(UUID id) {
        return contactRepository.findById(id).orElse(null);
    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact update(Contact contact) {
        if(contact.getId() == null) {
            return save(contact);
        }
        return contactRepository.update(contact);
    }

    public void delete(UUID id) {
        contactRepository.deleteById(id);
    }
}