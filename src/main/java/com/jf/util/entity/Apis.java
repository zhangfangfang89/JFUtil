package com.jf.util.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Apis {

    private int id;
    private String apiName;
    private String domain;
    private String api;
    private String type;
    private String params;
    private String result;


}
