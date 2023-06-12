package com.example.addressbook.controller.model;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@AllArgsConstructor
@Builder
@Getter
public class ListOfAddressesDto extends RepresentationModel<ListOfAddressesDto> {

    @Singular
    private final List<AddressListDto> addresses;

}
