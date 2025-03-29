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
    private List<Color> colors;
    private List<Size> sizes;

    public Product(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Product(String name, int quantity, List<String> images, List<Color> colors, List<Size> sizes) {
        this.name = name;
        this.quantity = quantity;
        this.images = images;
        this.colors = colors;
        this.sizes = sizes;
    }
}
