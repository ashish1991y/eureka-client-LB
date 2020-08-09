package com.ashish.client.application;

import com.ashish.client.utility.DiscoveryClientInstanceProvider;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
@RestController
@RequestMapping(value = "/v1/client")
public class ClientEndpoints {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://CALCULATOR-API/v1/";
 
    @GetMapping(value="/call-eureka-client")
    public String method() {
        String response =  restTemplate.getForObject(BASE_URL+"test",String.class);
        return "Response:" + response;
    }

    @PostMapping(value="/calculate-eureka-client")
    public String methodCalculator(@RequestBody SimpleCalculatorRequestDTO simpleCalculatorRequestDTO) throws RestClientException, URISyntaxException {
        MultiValueMap<String, String> headers= new LinkedMultiValueMap<>();
        headers.set("Content-Type","application/json");
        URI uri=new URI(BASE_URL + "calculate");
        RequestEntity<SimpleCalculatorRequestDTO> requestEntity =
                new RequestEntity<SimpleCalculatorRequestDTO>(simpleCalculatorRequestDTO,headers,HttpMethod.POST,uri);
        ResponseEntity<SimpleCalculatorResponseDTO> response= restTemplate.exchange(requestEntity, SimpleCalculatorResponseDTO.class);
        return "Instance called is : " + uri + "<br/><br/> And Response : " + response.getBody().getResult();
    }
}
