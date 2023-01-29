package com.docterryome.kroger.krogerapicart.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class KrogerPrice {
    private float regular;
    private float promo;
}
