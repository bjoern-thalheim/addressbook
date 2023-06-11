package com.example.addressbook;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.addressbook.controller.AddressController;
import com.example.addressbook.controller.model.AddressListDto;
import com.example.addressbook.persistence.repo.AddressRepository;

@SpringBootTest
class AddressControllerIT {

    @Inject
    AddressController controller;

    @Inject
    AddressRepository repository;

    @AfterEach
    public void purgeDb() {
        this.repository.deleteAll();
    }

    @Test
    public void testEmptyListIsReturnedWhenDatabaseIsEmpty() {
        // given: no interactions before, empty datadase
        // when
        List<AddressListDto> addresses = controller.getAddresses();
        assertThat(addresses).isEmpty();
    }

    @Test
    public void whenAddressIsWrittenIntoDbItShouldBeInTheListOfAddresses() {
        // given
        AddressListDto address = AddressListDto.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@gmail.com").build();
        controller.addAddress(address);
        // when
        List<AddressListDto> addresses = controller.getAddresses();
        // then
        assertThat(addresses).hasSize(1);
        AddressListDto item = addresses.iterator().next();
        assertThat(item.getFirstName()).isEqualTo(address.getFirstName());
        assertThat(item.getLastName()).isEqualTo(address.getLastName());
        assertThat(item.getEmail()).isEqualTo(address.getEmail());
    }
}