package com.jf.util.dao;


import com.jf.util.entity.Apis;
import org.springframework.stereotype.Repository;

@Repository
public interface ApisDao {
    Integer insert(Apis apis);
    Apis query(String apisName);
    Apis queryAll();
    boolean update(Apis apis);
    boolean queryApi(String api);
}
