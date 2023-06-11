package com.example.addressbook.persistence.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Setter
@Getter
@Entity
@Table(name = "addresses")
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "first_name", nullable = false)
    private String lastName;
    @Column(name = "last_name", nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String email;

    @ElementCollection
    @CollectionTable(name = "address_properties",
        joinColumns = { @JoinColumn(name = "address_id", referencedColumnName = "id") })
    @MapKeyColumn(name = "name")
    @Column(name = "attribute")
    @Singular
    private Map<String, String> attributes;

    public Address(String lastName, String firstName, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.attributes = new HashMap<String, String>();
    }
}
