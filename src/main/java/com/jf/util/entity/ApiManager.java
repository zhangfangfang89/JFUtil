package com.jf.util.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "t_api_manager")
public class ApiManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private int id;
    @Column(name = "api_name")
    private String apiName;
    private String domain;
    private String api;
    private String type;
    private String params;
    private String result;
}
