package com.example.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stock {

    private String id;
    private String epc;
    private String tid;
    private String userMemory;
    private String description;
    private String lastScanDate;
    private String idRoom;


}
