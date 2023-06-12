package com.example.addressbook.persistence.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.addressbook.persistence.model.Address;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class AddressRepositoryIT {

    @Inject
    AddressRepository repository;

    @Test
    public void repoShallReturnEmptyListOnFindAll() {
        // given: No interactions yet, DB is empty
        // when
        Iterable<Address> addresses = repository.findAll();
        // then
        assertThat(addresses).isEmpty();
    }

    @Test
    public void whenAddressIsWrittenIntoDbFindAllShouldFindOneResult() {
        // given
        Address address = new Address("John", "Doe", "john.doe@gmail.com");
        assertThat(address.getId()).isNull();
        repository.save(address);
        // when
        Iterable<Address> addresses = repository.findAll();
        // then
        assertThat(addresses).hasSize(1);
        assertThat(addresses.iterator().next()).isEqualTo(address);
        assertThat(addresses.iterator().next().getId()).isNotNull();
    }

    @Test
    public void whenADynamicPropertyIsSavedToTheDbIdIsFetchedAgain() {
        // given
        Address address = new Address("John", "Doe", "john.doe@gmail.com");
        address.getAttributes().put("nickname", "Stephen the Savage");
        repository.save(address);
        // when
        Iterable<Address> addresses = repository.findAll();
        // then
        assertThat(addresses).hasSize(1);
        Address savedAddress = addresses.iterator().next();
        assertThat(savedAddress).isEqualTo(address);
        assertThat(savedAddress.getAttributes().get("nickname")).isEqualTo("Stephen the Savage");
    }

    @Test
    public void whenAddressIsWrittenIntoDbItMayBeDeleted() {
        // given
        Address address = new Address("John", "Doe", "john.doe@gmail.com");
        repository.save(address);
        // when
        UUID id = address.getId();
        repository.deleteById(id);
        Iterable<Address> addresses = repository.findAll();
        // then
        assertThat(addresses).isEmpty();
    }

    @Test
    public void whenAPropertyIsDeletedItShallBeDeletedInTheDatabase() {
        // given
        Address address = new Address("John", "Doe", "john.doe@gmail.com");
        address.getAttributes().put("nickname", "Stephen the Savage");
        address.getAttributes().put("favouritecolor", "Black");
        repository.save(address);
        // when
        Address intermediate = repository.findById(address.getId()).get();
        intermediate.getAttributes().remove("favouritecolor");
        repository.save(intermediate);
        Address result = repository.findById(intermediate.getId()).get();
        // then
        assertThat(result.getAttributes().get("nickname")).isEqualTo("Stephen the Savage");
        assertThat(result.getAttributes()).hasSize(1);
    }

}