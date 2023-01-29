package com.docterryome.kroger.krogerapicart.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docterryome.kroger.krogerapicart.domain.KrogerStoreData;
import com.docterryome.kroger.krogerapicart.domain.KrogerToken;
import com.docterryome.kroger.krogerapicart.service.KrogerService;

@RestController
public class KrogerController {

    private KrogerService krogerService;

    public KrogerController(KrogerService krogerService){
        this.krogerService = krogerService;
    }

    @RequestMapping("/{type}")
    public KrogerToken getToken(@PathVariable String type){
        return this.krogerService.getBearerToken(type);
    }

    @RequestMapping("/location/{zipCode}")
    public KrogerStoreData getKrogerStoreData(@PathVariable String zipCode){
        return this.krogerService.getLocations(zipCode);
    }
}
