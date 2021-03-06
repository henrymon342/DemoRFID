package com.example.uhf_bt.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class stockList {
    private String id;
    private String EPC;
    private String TID;
    private String userMemory;
    private String description;
    private String lastScanDate;
    private String fkIdRoom;
}
