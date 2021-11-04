package com.jf.util.entity;


import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vo {


    private String name;
    private String step;
    private String bmobile;
    private String bpassword;
    private String cmobile;
    private String customerId;
    private String demandId;
    private String orderId;
    private String agentId;
    private String auntId;
    private String inputParams;


}

