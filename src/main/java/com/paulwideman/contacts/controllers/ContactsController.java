package com.paulwideman.contacts.controllers;

import com.paulwideman.contacts.dtos.Contact;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("contacts")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactsController {
    private final Map<Integer, Contact> contacts = new HashMap<>();
    private int nextId = 1;
    private final Object createLock = new Object();
    private final Map<Integer, Object> updateLocks = new HashMap<>();

    @RequestMapping("/")
    public Collection<Contact> getContacts() {
        return contacts.values();
    }

    @RequestMapping(path="/", method = RequestMethod.POST)
    public Contact createContact(@RequestBody Contact contact) {
        synchronized(createLock) {
            contact.id = nextId;
            contacts.put(nextId, contact);
            updateLocks.put(nextId, new Object());
            nextId++;
        }
        return contact;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Contact updateContact(@RequestBody Contact contact, @PathVariable int id) {
        if (!contacts.containsKey(id)) {
            // TODO: throw error that will result in HTTP 404
        }
        synchronized(updateLocks.get(id)) {
            Contact currentContact = contacts.get(id);
            contact.copyTo(currentContact);
            return currentContact;
        }
    }

    @RequestMapping("/{id}")
    public Contact getContact(@PathVariable int id) {
        if (!contacts.containsKey(id)) {
            // TODO: throw error that will result in HTTP 404
        }
        return contacts.get(id);
    }

    @RequestMapping(path="/{id}", method=RequestMethod.DELETE)
    public void deleteContact(@PathVariable int id) {
        if (!contacts.containsKey(id)) {
            // TODO: throw error that will result in HTTP 404
        }
        contacts.remove(id);
    }
}
