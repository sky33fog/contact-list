package com.example.contactlist.repository;

import com.example.contactlist.Contact;
import com.example.contactlist.exception.ContactNotFoundException;
import com.example.contactlist.repository.mapper.ContactRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = "app", name = "storage-type", havingValue = "db")
public class DatabaseContactRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        log.debug("Call findAll in DatabaseContactRepository");
        String sql = "SELECT * FROM contact";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> findById(UUID id) {
        log.debug("Call findById in DatabaseContactRepository with ID: " + id);
        String sql = "SELECT * FROM contact WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id.toString()}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
        ));
        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Call save in DatabaseContactRepository");
        contact.setId(UUID.randomUUID());
        String sql = "INSERT INTO contact (id, firstName, lastName, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                contact.getId().toString(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone());
        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Call update in DatabaseContactRepository");
        Contact existedContact = findById(contact.getId()).orElse(null);
        if(existedContact != null) {
            String sql = "UPDATE contact SET firstName = ?, lastName = ?, email = ?, phone = ?"
                    + "WHERE id = ?";
            jdbcTemplate.update(sql,
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getEmail(),
                    contact.getPhone(),
                    contact.getId().toString());
            return contact;
        }
        throw new ContactNotFoundException("Contact not found! ID: " + contact.getId());
    }

    @Override
    public void deleteById(UUID id) {
        log.debug("Call deleteById in DatabaseContactRepository with ID: " + id);
        String sql = "DELETE FROM contact WHERE id = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("Call batchInsert in DatabaseContactRepository with batch size: " + contacts.size());
        String sql = "INSERT INTO contact (id, firstName, lastName, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Contact contact = contacts.get(i);
                ps.setString(1, contact.getId().toString());
                ps.setString(2, contact.getFirstName());
                ps.setString(3, contact.getLastName());
                ps.setString(4, contact.getEmail());
                ps.setString(5, contact.getPhone());
            }

            @Override
            public int getBatchSize() {
                return contacts.size();
            }
        });
    }
}