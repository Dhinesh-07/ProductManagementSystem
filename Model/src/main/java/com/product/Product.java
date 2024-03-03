package com.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @ManyToMany
    private List<Brand> brands;

    private String productName;

    private String description;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    private double mrp;

    private double salePrice;
}