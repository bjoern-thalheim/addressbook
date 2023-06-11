package com.example.addressbook.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.addressbook.controller.model.AddressListDto;
import com.example.addressbook.persistence.model.Address;
import com.example.addressbook.persistence.repo.AddressRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressRepository repository;

    @GetMapping
    public List<AddressListDto> getAddresses() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .map(this::toAddressListDto)
            .collect(Collectors.toList());
    }

    private AddressListDto toAddressListDto(Address address) {
        return AddressListDto.builder()
            .firstName(address.getFirstName())
            .lastName(address.getLastName())
            .email(address.getEmail())
            .build();
    }

    @PostMapping
    public void addAddress(AddressListDto addressListDto) {
        Address address =
            new Address(addressListDto.getLastName(), addressListDto.getFirstName() , addressListDto.getEmail());
        repository.save(address);
    }
}
