package com.example.contactlist.repository;

import com.example.contactlist.Contact;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactRepository {

    List<Contact> findAll();

    Optional<Contact> findById(UUID id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(UUID id);

    void batchInsert(List<Contact> contacts);
}