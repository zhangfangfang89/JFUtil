package com.jf.util.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "t_configs")
public class Configs {
    //"keyName","text","mobile","password"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private int id;
    @Column(name = "key_name")
    private String keyName;
    private String text;
    private String mobile;
    private String password;

}
