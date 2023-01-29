package com.docterryome.kroger.krogerapicart.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String addressLine1; 
    private String city;
    private String state;
    private String zipCode;
    private String county;
}
