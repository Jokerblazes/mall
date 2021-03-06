package com.jx.mall.repository;

import com.jx.mall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByName(String name);

    public List<Product> findByNameLikeAndAndDescriptionLike(String name, String description);
}
