package com.momchil.TU4ALL.dbo;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleDBO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private long id;

    @Column(nullable = false,unique = true)
    private String name;

}
