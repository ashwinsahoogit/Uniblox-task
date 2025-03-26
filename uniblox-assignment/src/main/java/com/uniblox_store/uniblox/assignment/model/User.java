package com.uniblox_store.uniblox.assignment.model;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String type; // user or admin
}
