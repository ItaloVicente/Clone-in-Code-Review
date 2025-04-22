        byte[] decryptedBytes = provider.decrypt(encryptedBytes);
        String decryptedString = new String(decryptedBytes, Charset.forName("UTF-8"));
        decrypted = JacksonTransformers.MAPPER.readValue(decryptedString, Object.class);
        if (decrypted instanceof Map) {
            decrypted = JsonObject.from((Map<String, ?>) decrypted);
        } else if (decrypted instanceof List) {
            decrypted = JsonArray.from((List<?>) decrypted);
        }
        return decrypted;
