package org.example.session1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Size {
    private int id;
    private String name;

    public Size(String name) {
        this.name = name;
    }
}
