package com.laibao.dubbo.serialization.test;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String name;
    private String email;

}
