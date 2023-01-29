package com.docterryome.kroger.krogerapicart.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class KrogerDataList {
    private List<KrogerProduct> data;
}