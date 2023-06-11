package com.example.addressbook.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class AddressListDto {
    private String firstName;
    private String lastName;
    private String email;
}
