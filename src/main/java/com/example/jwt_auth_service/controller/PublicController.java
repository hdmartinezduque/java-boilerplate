package com.example.jwt_auth_service.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/public")
public class PublicController {



    @GetMapping("/test")
    public Map<String, String> publicEndpoint() {
        return Map.of("message", "Este endpoint es público");
    }

    @GetMapping("/list")
    public ResponseEntity<Object> publicList() {

        List<String> tempPublicUrls = new ArrayList<>(Arrays.asList(
                "v1/public/test",
                "v1/protected/list",
                "v1/files/downloadFiles"
        ));

        List<String> thirdPartyURLs = Arrays.asList(
                "v1/thirdParty/queries",
                "v2/thirdParty/queries",
                "v3/thirdParty/queries",
                "v4/thirdParty/queries",
                "v5/thirdParty/queries");
        Collections.sort(thirdPartyURLs, (a, b) -> a.compareTo(b)); //lambda
        List<String> docsURLs = Arrays.asList("v1/docs/API");
        tempPublicUrls.add("v1/docs/");
        tempPublicUrls.addAll(docsURLs);
        tempPublicUrls.addAll(thirdPartyURLs);


        Map<String, String> urlObject = new LinkedHashMap<>();

        tempPublicUrls.forEach(url -> {
            String[] parts = url.split("/");
            if (parts.length>1){
                String key = parts[0] + "/" + parts[1];
                urlObject.put(key, url);
            }
        });
        
        return ResponseEntity.status(HttpStatus.OK).body(urlObject);
    }

}
