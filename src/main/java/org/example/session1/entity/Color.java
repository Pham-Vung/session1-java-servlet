package org.example.session1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Color {
    private int id;
    private String name;

    public Color(String name) {
        this.name = name;
    }
}
