package com.paulwideman.contacts.dtos;

public class Contact {
    // only using public member fields for simplicity
    public int id;
    public String firstName;
    public String lastName;
    public String email;

    public void copyTo(Contact otherContact) {
        if (null != firstName && firstName.length() > 0) {
            otherContact.firstName = firstName;
        }
        if (null != lastName && lastName.length() > 0) {
            otherContact.lastName = lastName;
        }
        if (null != email && email.length() > 0) {
            otherContact.email = email;
        }
    }
}
