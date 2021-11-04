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
@Table(name = "t_sence_manager")
public class SenceManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;
    private String name;
    private String step;
    private String cmobile;
    private String bmobile;
    private String bpassword;
    @Column(name="customer_id")
    private String customerId;
    @Column(name="demand_id")
    private String demandId;
    @Column(name="order_id")
    private String orderId;
    @Column(name="agent_id")
    private String agentId;
    @Column(name="aunt_id")
    private String auntId;

}
