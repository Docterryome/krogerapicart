package com.docterryome.kroger.krogerapicart.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KrogerStore  {
    private String locationId;
    private String chain;
    private Address address;
    private String name;
}
