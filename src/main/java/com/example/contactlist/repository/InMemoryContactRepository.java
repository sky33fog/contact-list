package com.example.contactlist.repository;

import com.example.contactlist.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
@ConditionalOnProperty(prefix = "app", name = "storage-type", havingValue = "ram")
public class InMemoryContactRepository implements ContactRepository {

    List<Contact> contacts = new ArrayList<>();

    @Override
    public List<Contact> findAll() {
        log.debug("Call findAll in InMemoryContactRepository");
        return contacts;
    }

    @Override
    public Optional<Contact> findById(UUID id) {
        log.debug("Call findById in InMemoryContactRepository");
        return contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Call save in InMemoryContactRepository");
        contact.setId(UUID.randomUUID());
        contacts.add(contact);
        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Call update in InMemoryContactRepository");
        Contact existedContact = findById(contact.getId()).orElse(null);
        if(existedContact != null) {
            existedContact.setFirstName(contact.getFirstName());
            existedContact.setLastName(contact.getLastName());
            existedContact.setEmail(contact.getEmail());
            existedContact.setPhone(contact.getPhone());
        }
        return contact;
    }

    @Override
    public void deleteById(UUID id) {
        log.debug("Call deleteById in InMemoryContactRepository");
        findById(id).ifPresent(contacts ::remove);
    }

    @Override
    public void batchInsert(List<Contact> contactList) {
        contacts.addAll(contactList);
    }
}