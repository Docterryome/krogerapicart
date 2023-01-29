package com.docterryome.kroger.krogerapicart.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class KrogerProduct {

    private String productId;
    private String upc;
    private String brand;
    private String description;
    private List<KrogerItemDetail> items;
    
}
