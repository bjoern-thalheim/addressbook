package com.example.addressbook.controller.model;

import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class AddressListDto extends RepresentationModel<AddressListDto> {
    UUID id;

    private String firstName;
    private String lastName;
    private String email;
}
