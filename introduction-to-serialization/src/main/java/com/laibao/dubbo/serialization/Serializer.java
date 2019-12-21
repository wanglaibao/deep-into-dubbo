package com.laibao.dubbo.serialization;

public interface Serializer {

    /**
     * 序列化
     * @param obj
     * @return byte[]
     */
    <T> byte[] serialize(T obj);


    /**
     * 反序列化
     *
     * @param data
     * @param clz
     * @return T
     */
     <T> T deserialize(byte[] data, Class<T> clz);
}
