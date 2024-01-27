package com.example.contactlist;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@Data
@FieldNameConstants
public class Contact {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}