package com.jx.mall.service;

import com.jx.mall.entity.Inventory;
import com.jx.mall.entity.Product;
import com.jx.mall.repository.InventoryRepository;
import com.jx.mall.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public Product addProduct(Product product) {
        Product actualProduct = productRepository.save(product);

        createInventory(actualProduct);

        return actualProduct;
    }

    @Transactional
    public boolean updateProduct(Product product, Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product actualProduct = optionalProduct.get();
            product.setId(actualProduct.getId());
            productRepository.save(product);
            return true;
        }
        return false;
    }

    private void createInventory(Product addedProduct) {
        Inventory inventory = new Inventory();
        inventory.setProductId(addedProduct.getId());
        inventory.setCount(0L);
        inventoryRepository.save(inventory);
    }
}
