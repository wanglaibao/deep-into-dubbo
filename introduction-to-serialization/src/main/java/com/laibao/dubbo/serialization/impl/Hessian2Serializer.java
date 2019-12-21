package com.laibao.dubbo.serialization.impl;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.laibao.dubbo.serialization.Serializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class Hessian2Serializer implements Serializer {


    public byte[] serialize(Object obj) {
        Objects.requireNonNull(obj,"obj cannot be null");
        ByteArrayOutputStream outputStream = null;
        HessianOutput hessianOutput = null;
        try {
            outputStream = new ByteArrayOutputStream(1024);
            hessianOutput = new HessianOutput (outputStream);
            hessianOutput.startCall();
            hessianOutput.writeObject(obj);
            hessianOutput.completeCall();
            return outputStream.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to serialize",ex);
        }finally {
            if (hessianOutput != null) {
                try {
                    hessianOutput.close();
                }
                catch (Exception ex) {
                    throw new RuntimeException("Failed to close stream",ex);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (Exception ex) {
                    throw new RuntimeException("Failed to close stream",ex);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        Objects.requireNonNull(data,"data cannot be null");
        ByteArrayInputStream inputStream = null;
        HessianInput hessianInput = null;
        T obj = null;
        try {

            inputStream = new ByteArrayInputStream(data);
            hessianInput = new HessianInput(inputStream);
            hessianInput.startReply();
            obj = (T)hessianInput.readObject();
            hessianInput.completeReply();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }catch (final Throwable e) {
            e.printStackTrace();
        }finally {
            if (hessianInput != null) {
                try {
                    hessianInput.close();
                }
                catch (Exception ex) {
                    throw new RuntimeException("Failed to close stream",ex);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (Exception ex) {
                    throw new RuntimeException("Failed to close stream",ex);
                }
            }
        }
        return obj;

    }
}
