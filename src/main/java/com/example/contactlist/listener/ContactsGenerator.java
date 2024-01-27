package com.example.contactlist.listener;

import com.example.contactlist.Contact;
import com.example.contactlist.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "contacts-generator.enabled")
public class ContactsGenerator {

    private final ContactRepository contactRepository;

    @EventListener(ApplicationStartedEvent.class)
    private void generateContacts() {
        List<Contact> contactList = new ArrayList<>();
        for(int i = 1; i < 11; i++) {
            Contact contact = new Contact();
            contact.setId(UUID.randomUUID());
            contact.setFirstName("FN" + i);
            contact.setLastName("LN" + i);
            contact.setEmail(contact.getFirstName() + "_" + contact.getLastName() + "@email.com");
            contact.setPhone("+712345678" + i);
            contactList.add(contact);
        }
        contactRepository.batchInsert(contactList);
    }
}