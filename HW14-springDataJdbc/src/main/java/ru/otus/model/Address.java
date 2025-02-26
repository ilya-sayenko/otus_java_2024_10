package ru.otus.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Table(name = "address")
public record Address(
        @Id
        @Column("client_id")
        Long id,

        @Column("street")
        String street
){
}
