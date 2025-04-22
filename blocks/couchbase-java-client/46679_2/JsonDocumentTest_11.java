package com.couchbase.client.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationHelper {

    public static <T extends Serializable> byte[] serializeToBytes(T input) throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(input);
        objectStream.close();
        return byteStream.toByteArray();
    }

    public static <T extends Serializable> T deserializeFromBytes(byte[] bytes, Class<T> clazz) throws Exception {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        Object output = objectStream.readObject();
        return clazz.cast(output);
    }

}
