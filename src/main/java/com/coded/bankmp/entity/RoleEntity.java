package com.coded.bankmp.entity;


import com.coded.bankmp.util.Roles;

import javax.persistence.*;

@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Roles title;

    public RoleEntity(Roles title) {
        this.title = title;
    }

    public RoleEntity() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Roles getTitle() {
        return title;
    }

    public void setTitle(Roles title) {
        this.title = title;
    }
}
