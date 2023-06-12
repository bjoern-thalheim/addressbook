package com.example.addressbook.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.example.addressbook.controller.model.AddressListDto;
import com.example.addressbook.controller.model.ListOfAddressesDto;
import com.example.addressbook.persistence.model.Address;
import com.example.addressbook.service.AddressService;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Inject
    AddressController controller;

    @MockBean
    AddressService service;

    @Test
    void whenAddressListIsRequestedLinksForCreateAndSelfRelShouldBeProvided() {
        // when
        ListOfAddressesDto addresses = controller.getOverview();
        // then
        assertThat(addresses).isInstanceOf(RepresentationModel.class);
        assertThat(addresses.getLinks()).hasSize(1);
        assertThat(addresses.getLinks("self")).hasSize(1);
        assertThat(addresses.getLinks("self").get(0)).isEqualTo(Link.of("/api/addresses", "self"));
    }

    @Test
    void whenAddressIsInServiceLayerAnUpdateLinkShallBeProvided() {
        // given
        Address address = this.createAddress("John", "Doe", "john.doe@gmail.com", UUID.randomUUID());
        when(service.getAllAddresses())
            .thenReturn(
                List.of(address));
        // when
        List<AddressListDto> addresses = controller.getOverview().getAddresses();
        // then
        assertThat(addresses).hasSize(1);
        AddressListDto addressListDto = addresses.get(0);
        List<Link> edit = addressListDto.getLinks("update");
        assertThat(edit).hasSize(1);
        assertThat(edit.get(0).getHref()).isEqualTo("/api/addresses/update/" + address.getId().toString());
    }

    Address createAddress(String firstname, String lastname, String email, UUID uuid) {
        Address result = new Address();
        result.setFirstName(firstname);
        result.setLastName(lastname);
        result.setEmail(email);
        result.setId(uuid);
        return result;
    }
}