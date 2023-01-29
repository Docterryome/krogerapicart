package com.docterryome.kroger.krogerapicart.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KrogerToken {
    private int expires_in;
    private String access_token;
    private String token_type;
}
