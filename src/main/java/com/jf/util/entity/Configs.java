package com.jf.util.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Configs {
    private int id;
    private String keys;
    private String text;
    private String mobile;
    private String password;
}
