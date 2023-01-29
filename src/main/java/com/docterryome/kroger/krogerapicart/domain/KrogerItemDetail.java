package com.docterryome.kroger.krogerapicart.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class KrogerItemDetail {
    private String itemId;
    private KrogerPrice price;
    private String size;
}


