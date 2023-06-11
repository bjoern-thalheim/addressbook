package com.example.addressbook.persistence.repo;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.addressbook.persistence.model.Address;

public interface AddressRepository extends CrudRepository<Address, UUID> {
}
