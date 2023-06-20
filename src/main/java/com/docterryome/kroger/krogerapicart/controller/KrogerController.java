package com.docterryome.kroger.krogerapicart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.docterryome.kroger.krogerapicart.domain.KrogerDataList;
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

    @RequestMapping("/products")
    public List<HashMap<String,String>> getProductDetails(@RequestParam String locationId, @RequestParam String productSearch){
        KrogerDataList dataList = this.krogerService.getDataList(locationId, productSearch);
        List<HashMap<String, String>> fMaps = new ArrayList<HashMap<String, String>>();
        dataList.getData().forEach(data -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("kroger.productId", data.getProductId());
            map.put("kroger.description", data.getDescription());
            map.put("kroger.upc", data.getUpc());
            map.put("kroger.brand", data.getBrand());
            data.getItems().forEach(item -> {
                map.put("kroger.itemId", item.getItemId());
                map.put("kroger.itemSize", item.getSize());
                map.put("kroger.price.regular", String.valueOf(item.getPrice().getRegular()));
                map.put("kroger.price.promo", String.valueOf(item.getPrice().getPromo()));
            });
            fMaps.add(map);
        });
        return fMaps;
    }
}
