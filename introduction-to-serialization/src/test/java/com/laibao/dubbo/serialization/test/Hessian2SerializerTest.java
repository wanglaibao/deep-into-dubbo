package com.laibao.dubbo.serialization.test;

import com.laibao.dubbo.serialization.impl.Hessian2Serializer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hessian2SerializerTest {

    @Test
    public void testSerializeAndDeserialize() {
        UserListAndMap userListAndMap = new UserListAndMap();
        List<User> userList = new ArrayList();
        Map<String, User> userMap = new HashMap();

        User firstUser = new User();
        firstUser.setEmail("jinge@163.com");
        firstUser.setName("dddddddd");

        User secondUser = new User();
        secondUser.setEmail("alading@163.com");
        secondUser.setName("dddddd");


        userList.add(firstUser);
        userList.add(secondUser);
        userMap.put("firstUser", firstUser);
        userMap.put("secondUser",secondUser);

        userListAndMap.setUserList(userList);
        userListAndMap.setUserMap(userMap);

        Hessian2Serializer hessian2Serializer = new Hessian2Serializer();

        byte[] userBytes = hessian2Serializer.serialize(firstUser);
        System.out.println(new String(userBytes));


        User user1 = hessian2Serializer.deserialize(userBytes,User.class);
        System.out.println(user1.getEmail() + " : " + user1.getName());


       // UserListAndMap userListAndMap1 = (UserListAndMap) new Hessian2Serializer().deserialize(userBytes, UserListAndMap.class);

        //System.out.println(user.getEmail() + " : " + user.getName() + " : " + new String(new JSONSerializer().serialize(u.getUserList())) + " : " + new String(new JSONSerializer().serialize(u.getUserMap())));

        //userListAndMap1.getUserList().forEach(System.out::println);
        //userListAndMap1.getUserMap().forEach((key, value) -> System.out.println("the key is : "+key+" and the value is : "+value));

    }
}
