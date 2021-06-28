package com.example.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lector {
    private String id;
    private String alias;
    private String marca;
    private String modelo;
    private String description;
    private String macAddress;
}
