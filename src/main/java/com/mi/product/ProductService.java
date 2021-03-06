package com.mi.product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Service
public class ProductService {

    final private static Logger log = LoggerFactory.getLogger(ProductService.class);

    @Value("${data.location}")
    private String dataLocation;

    @Value("${data.filename}")
    private String dataFilename;

    public List<Product> getProducts(String factory) {
        List<Product> result = null;

        List<Product> products = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type type = new TypeToken<List<Product>>() {
        }.getType();

        InputStream stream = null;
        try {
            if (Files.exists(Paths.get(dataLocation + File.separator + dataFilename))) {
                stream = new FileInputStream(dataLocation + File.separator + dataFilename);
            } else {
                stream = this.getClass().getClassLoader().getResourceAsStream(dataFilename);
            }

            products = gson.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), type);
        } catch (FileNotFoundException e) {
            result = new ArrayList<>();
        }

        result = products.stream().filter(p -> p.getFactory().equals(factory)).collect(Collectors.toList());

        log.info("loaded: {}", gson.toJson(result));
        return result;
    }

}
