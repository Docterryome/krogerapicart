package com.docterryome.kroger.krogerapicart.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.docterryome.kroger.krogerapicart.config.KrogerConfig;
import com.docterryome.kroger.krogerapicart.domain.KrogerStoreData;
import com.docterryome.kroger.krogerapicart.domain.KrogerToken;

@Service
@DependsOn("krogerConfig")
@CacheConfig(cacheNames = {"cartToken", "productToken", "profileToken", "publicToken"})
public class KrogerService {

    private WebClient webClient;


    @Autowired
    private KrogerService(KrogerConfig krogerConfig){
        this.webClient = WebClient.builder()
        .baseUrl("https://api.kroger.com/v1/")
        .defaultHeaders(httpHeader ->  httpHeader.setBasicAuth(krogerConfig.getId(), krogerConfig.getSecret()))
        .build();
    }


    public KrogerToken getBearerToken(String type){
           System.out.println("The type " + type);
        if(type.equals("cart")){
                System.out.println("Getting Cart Token");
            return this.getCartToken();
        }
        else if(type.equals("product")){
                System.out.println("Getting Product Token");
            return this.getProductToken();
        }
        else if(type.equals("profile")){
            return this.getProfileToken();
        }
        else {
            return this.getPublicToken();
        }
    }

    @Cacheable("cartToken")
    private KrogerToken getCartToken(){
        return this.webClient.post()
        .uri("/connect/oauth2/token")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData("grant_type", "client_credentials")
        .with("scope", "cart.basic.write"))
        .retrieve()
        .bodyToMono(KrogerToken.class)
        .block();
    }

    @Cacheable("productToken")
    private KrogerToken getProductToken(){
        System.out.println("Getting Token!");
        return this.webClient.post()
        .uri("/connect/oauth2/token")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData("grant_type", "client_credentials")
        .with("scope", "product.compact"))
        .retrieve()
        .bodyToMono(KrogerToken.class)
        .block();
    }

    @Cacheable("profileToken")
    private KrogerToken getProfileToken(){
        return this.webClient.post()
        .uri("/connect/oauth2/token")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData("grant_type", "client_credentials")
        .with("scope", "product.compact"))
        .retrieve()
        .bodyToMono(KrogerToken.class)
        .block();
    }

    @Cacheable("publicToken")
    private KrogerToken getPublicToken(){
        return this.webClient.post()
        .uri("/connect/oauth2/token")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData("grant_type", "client_credentials"))
        .retrieve()
        .bodyToMono(KrogerToken.class)
        .block();
    }

    public KrogerStoreData getLocations(String zipCode){
        return this.webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/locations")
        .queryParam("filter.zipCode.near", zipCode)
        .queryParam("filter.chain", "Kroger")
        .build())
        .headers(httpHeaders -> httpHeaders.setBearerAuth(this.getBearerToken("public").getAccess_token()))
        .retrieve()
        .onStatus(httpStatus -> httpStatus.is4xxClientError(), response -> response.bodyToMono(String.class).map(Exception::new))
        .bodyToMono(KrogerStoreData.class)
        .block();
    }
}