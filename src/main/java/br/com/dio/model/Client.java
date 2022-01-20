package br.com.dio.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne private Address address;
}
