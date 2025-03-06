package org.example.session1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Image {
    private int id;
    private String name;
    private int productId;

    public Image(String name, int productId) {
        this.name = name;
        this.productId = productId;
    }
}
