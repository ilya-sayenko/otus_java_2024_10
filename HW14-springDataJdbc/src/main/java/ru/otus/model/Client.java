package ru.otus.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Builder
@Table(name = "client")
public record Client(
        @Id
        @Column("id")
        Long id,

        @Column("name")
        String name,

        @MappedCollection(idColumn = "client_id")
        Address address,

        @MappedCollection(idColumn = "client_id")
        Set<Phone> phones
){
}
