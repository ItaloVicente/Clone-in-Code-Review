        jsonObject.setCryptoManager(cryptoManager);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("myValue","The old grey goose jumped over the wrickety gate.");
        map.put("myInt", 10);
        Assert.assertEquals(jsonObject.toDecryptedMap("AES").get("message"), map);
    }

    @Test
    public void testEncryptStringUsingRSA() throws Exception {
        JsonObject jsonObject = JsonObject.create();
        JsonObject encrypted = JsonObject.create();
        encrypted.put("alg", "RSA-2048-OAEP-SHA1");
        encrypted.put("kid", "MyPublicKeyName");
        encrypted.put("ciphertext", "JMAfWsDrRdrF/ghjq4xlysN7F6TogIWhkf8Fa6Qvfhqq+syKXmtviIMVSBOXPJvZm8gT/wyryjqBrLFPK9AeeS2mI4FsPCbZzvRS85f12GS0TNIp71wW1R2BNFt+51Oa5jD1SOGT/qrBbpFWICVh2z+8AUbxirrobAFn3L179sAmcj8mP3Hl0+4YeTMx/DI333bsGvpB7bpk4U28/38PMhR8Uc1xjXpW1NUwfqho4PUPMumDPUa5e8p2DMUaGzgl6PVZmX3xqHGpBviWqcKGORQhOsWfI/45IDaWJlk7eHv8yuvJleKKd1cUkkRfZ3R1bui2F9S/HvJdQKClfHc3Ow==");
        jsonObject.put("__crypt_message" , encrypted);
        jsonObject.setCryptoManager(cryptoManager);
        Assert.assertEquals(jsonObject.toDecryptedMap("RSA").get("message"), "The old grey goose jumped over the wrickety gate.");
    }

    @Test
    public void testEncryptIntegerUsingRSA() throws Exception {
        JsonObject jsonObject = JsonObject.create();
        JsonObject encrypted = JsonObject.create();
        encrypted.put("alg", "RSA-2048-OAEP-SHA1");
        encrypted.put("kid", "MyPublicKeyName");
        encrypted.put("ciphertext", "NTxRjexminStAPC2fv6jXbaLPk4wxOkI8xU4EgmR05qZzmfjN+U1e5Ipi9mC/dOk0RfFklS+30ss45GkpZZnYjlEKQ9gI4ZmTb5Za42SHB6hhrR1ZXCIGfH4UhUwQiM+XzHLvOOiTnxVcSQd3TgL/hlTUbUsIWsqrm5Q9O1R+h8suEjcOnu4mmRI1qMdQLSKXPtqa8N1u00F24QNtS79UeWaZFVqll7FyESyJaz86ZS1/0NXwkfCwPRD0iP7Q/mfKh5+Vtl22PM9k1ar3aHbkJhE11Pm5y7w0Z9K1X73CmcSWYBuOY/SDpIBmqLYtv4o1ANB+bMv7yo+uoCouFrD/A==");
        jsonObject.put("__crypt_message" , encrypted);
        jsonObject.setCryptoManager(cryptoManager);
        Assert.assertEquals(jsonObject.toDecryptedMap("RSA").get("message"), 10);
    }

    @Test
    public void testEncryptArrayUsingRSA() throws Exception {
        JsonObject jsonObject = JsonObject.create();
        JsonObject encrypted = JsonObject.create();
        encrypted.put("alg", "RSA-2048-OAEP-SHA1");
        encrypted.put("kid", "MyPublicKeyName");
        encrypted.put("ciphertext", "KlYYMyaJRZvNr+tyoK5E75lE+QWSrsvmraBoapl/l9RRHkjien7+AqcmVsS/dRRa9Ad8dmyRvaOA9B46TsJ2FbzNJ8cVNTyLPdAeluU9aM0IiIuMfEYFc5XNC2clpQlsVgxMutiO0wiCEvFX3iNIvZFeYUQofKoe0H1VlyGGZbfLLdNfl+Rlui0IULFTW6UZEnmiIlTxffnGdvlwWlaTpJMfTAIYOieZiPbsraGgjIpPrQtXVSyy/bSqmOp2eva+X7dtD7R3vAHlRptvD4Muhp3jaxIQj0J4NsD4Gw+muHFYG1YnsdJERyWlkMQmnJt89XPL2VUD6ni2Q8TyxFm0LA==");
        jsonObject.put("__crypt_message" , encrypted);
        jsonObject.setCryptoManager(cryptoManager);
        Assert.assertEquals(jsonObject.toDecryptedMap("RSA").get("message"), Arrays.asList("The", "Old", "Grey","Goose","Jumped","over","the","wrickety","gate"));
    }

    @Test
    public void testEncryptObjectUsingRSA() throws Exception {
        JsonObject jsonObject = JsonObject.create();
        JsonObject encrypted = JsonObject.create();
        encrypted.put("alg", "RSA-2048-OAEP-SHA1");
        encrypted.put("kid", "MyPublicKeyName");
        encrypted.put("ciphertext", "E2Tlzl6MFTYnnKip7ENdZL8NuA/XkWPllWXu/nws4lYKHxVg8A1XYo+Q229q145glk73S01QmHbB0CZXbzvTo/BgZBb3but1U97qsoPnajFo6BVvigpCt6gaZnYSHuoXsB4L/JBRJuw+0cLX5sr7PsRb5WTV3eTCo/ja2jnqUSOCbWmwasqBY49dvSuJfTwWLcgOWJeg58AZoAGZEqAkuavoxD/+vtRXbFLXO3qdQ4XhsJjnttnaQnjgpKJOzYYwhpF7U0pW0YzT7PvtbJdguMeiYwd0Ypt5L7WiLr89ft0RFDO6K+x66fnxk4hM1c/xeCOAlNR/Mu75ke1/QZpnGg==");
        jsonObject.put("__crypt_message" , encrypted);
        jsonObject.setCryptoManager(cryptoManager);
