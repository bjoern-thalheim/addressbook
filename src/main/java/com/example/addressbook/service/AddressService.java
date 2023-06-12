package com.example.addressbook.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.addressbook.persistence.model.Address;
import com.example.addressbook.persistence.repo.AddressRepository;

@Service
public class AddressService {

    @Inject
    AddressRepository repository;

    public List<Address> getAllAddresses() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Address getAddress(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    public Address create(Address address) {
        return repository.save(address);
    }

    public Address update(Address address) {
        return repository.save(address);
    }

    public void delete(Address address) {
        repository.delete(address);
    }
}
