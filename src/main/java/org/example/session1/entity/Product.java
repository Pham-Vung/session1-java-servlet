package org.example.session1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private int quantity;
    private List<String> images;
//    private List<Color> colors;

    public Product(String name, int quantity, List<String> images) {
        this.name = name;
        this.quantity = quantity;
        this.images = images;
//        this.colors = colors;
    }
}
