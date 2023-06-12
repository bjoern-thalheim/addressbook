package com.example.addressbook.controller;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.addressbook.controller.model.AddressListDto;
import com.example.addressbook.controller.model.ListOfAddressesDto;
import com.example.addressbook.persistence.model.Address;
import com.example.addressbook.service.AddressService;

import lombok.RequiredArgsConstructor;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService service;

    @GetMapping
    public ListOfAddressesDto getOverview() {
        Link selfRel = linkTo(AddressController.class).withSelfRel();
        ListOfAddressesDto result = ListOfAddressesDto.builder()
            .addresses(service.getAllAddresses().stream().map(this::toAddressListDto).collect(Collectors.toList()))
            .build();
        result.add(selfRel);
        return result;
    }

    @PostMapping
    public AddressListDto addAddress(@RequestBody AddressListDto addressListDto) {
        Address address =
            new Address(addressListDto.getLastName(), addressListDto.getFirstName(), addressListDto.getEmail());
        Address savedAddress = service.create(address);
        return toAddressListDto(savedAddress);
    }

    @PutMapping(path = "/update/{id}")
    public AddressListDto update(@PathVariable String id, @RequestBody AddressListDto address) {
        Address foundAddress = service.getAddress(UUID.fromString(id));
        foundAddress.setFirstName(address.getFirstName());
        foundAddress.setLastName(address.getLastName());
        foundAddress.setEmail(address.getEmail());
        Address newAddress = service.update(foundAddress);
        return toAddressListDto(newAddress);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<AddressListDto> delete(@PathVariable String id) {
        Address address = service.getAddress(UUID.fromString(id));
        service.delete(address);
        AddressListDto result = AddressListDto.builder()
            .firstName(address.getFirstName())
            .lastName(address.getLastName())
            .email(address.getEmail())
            .id(address.getId())
            .build();
        return ResponseEntity.ok(result);
    }

    private AddressListDto toAddressListDto(Address address) {
        AddressListDto result = AddressListDto.builder()
            .firstName(address.getFirstName())
            .lastName(address.getLastName())
            .email(address.getEmail())
            .id(address.getId())
            .build();
        Link updateLink = linkTo(methodOn(this.getClass()).update(result.getId().toString(), result)).withRel("update");
        Link deleteLink = linkTo(methodOn(this.getClass()).delete(result.getId().toString())).withRel("delete");
        result.add(updateLink, deleteLink);
        return result;
    }
}
