package com.docterryome.kroger.krogerapicart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.docterryome.kroger.krogerapicart.config.KrogerConfig;
import com.docterryome.kroger.krogerapicart.domain.KrogerDataList;
import com.docterryome.kroger.krogerapicart.domain.KrogerStoreData;
import com.docterryome.kroger.krogerapicart.domain.KrogerToken;
import com.docterryome.kroger.krogerapicart.service.KrogerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@DependsOn("krogerConfig")
public class KrogerController {

    private KrogerService krogerService;
    private KrogerConfig krogerConfig;
    private static String URL = "https://api.kroger.com/v1/connect/oauth2/authorize";

    public KrogerController(KrogerService krogerService, KrogerConfig krogerConfig){
        this.krogerService = krogerService;
        this.krogerConfig = krogerConfig;
    }


    @RequestMapping("/kroger/login")
    public void krogerLogin(HttpServletResponse response){
        response.addHeader("Content-Type","application/x-www-form-urlencoded");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("location", URL + "?scope=art.basic.write&response_type=code&client_id=" 
        + this.krogerConfig.getId() + "&redirect_uri=http://localhost:8080/kroger/callback");
        response.setStatus(302);
    }

    @RequestMapping("kroger/callback")
    public void getStuffFromResponse(@RequestParam String code){
        System.out.println(code);
    }

    @RequestMapping("/kroger/{type}")
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
