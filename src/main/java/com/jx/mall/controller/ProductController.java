package com.jx.mall.controller;

import com.jx.mall.entity.Product;
import com.jx.mall.repository.ProductRepository;
import com.jx.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author Joker
 * @Description
 * @Date Create in 上午10:21 2018/5/22
 */
@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Value("${app.host}")
    private String host;

    @Value("${app.port}")
    private String port;

    /**
     * get Products
     * @param name
     * @param description
     * @return
     */
    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String description) {
        if (name == null) {
            return productRepository.findAll();
        } else if (description == null) {
            return productRepository.findByName(name);
        } else {
            name = addHeadAndTailPercent(name);
            description = addHeadAndTailPercent(description);
            return productRepository.findByNameLikeAndAndDescriptionLike(name, description);
        }
    }


    private String addHeadAndTailPercent(String string) {
        return "%" + string + "%";
    }


    /**
     * add a product
     * @param product
     * @return
     */
    @PostMapping()
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        HttpHeaders headers = new HttpHeaders();

        headers.add("location", String.format("http://%s:%s/products/%d", host, port, savedProduct.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * get a product by id
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Product getProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.isPresent() ? productOptional.get() : null;
    }

    /**
     * update a product
     * @param id
     * @param product
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        boolean result = productService.updateProduct(product, id);
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }
}
