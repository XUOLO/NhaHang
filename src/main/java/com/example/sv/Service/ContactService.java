package com.example.sv.Service;

 import com.example.sv.Model.Contact;
import com.example.sv.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    List<Contact> getAllContact(){ return  contactRepository.findAll();}



    public void saveContact(Contact contact) {
        this.contactRepository.save(contact);

    }

}
