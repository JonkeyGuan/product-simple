package com.mi.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
@RequestMapping("/api")
public class Resource {

    @Autowired
    private ProductService service;

    @GetMapping(path = "/products")
    public List<Product> products() {
        return service.getCustomers();
    }
}
