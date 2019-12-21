package com.laibao.dubbo.serialization.test;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Data
public class UserListAndMap implements Serializable {
    private List<User> userList;
    private Map<String, User> userMap;
}
