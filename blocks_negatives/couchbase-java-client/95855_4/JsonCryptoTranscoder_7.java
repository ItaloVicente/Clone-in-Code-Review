
            String jsonValue = JacksonTransformers.MAPPER.writeValueAsString(value);
            String keyName = config.getEncryptionKey() != null ? config.getEncryptionKey() : provider.getKeyName();

            encryptedVal.put("kid", keyName);
            encryptedVal.put("alg", config.getProvider().toString());

            int ivSize = provider.getIVSize();
            String encryptedValString;

            if (ivSize > 0) {
                byte[] encryptedwithIv = provider.encrypt(jsonValue.getBytes());
                byte[] iv = new byte[ivSize];
                byte[] encryptedBytes = new byte[encryptedwithIv.length - ivSize];
                System.arraycopy(encryptedwithIv, 0, iv, 0, ivSize);
                System.arraycopy(encryptedwithIv, ivSize, encryptedBytes, 0, encryptedBytes.length);
                encryptedVal.put("iv", Base64.encode(iv));
                encryptedVal.put("ciphertext", Base64.encode(encryptedBytes));
                encryptedValString = encryptedVal.getString("kid") + encryptedVal.getString("alg")
                        + encryptedVal.getString("iv") + encryptedVal.getString("ciphertext");
            } else {
                encryptedVal.put("ciphertext", Base64.encode(provider.encrypt(jsonValue.getBytes())));
                encryptedValString = encryptedVal.getString("kid") + encryptedVal.getString("alg")
                        + encryptedVal.getString("ciphertext");
            }

            String signatureKeyName = keyName;
            if (provider instanceof AESCryptoProviderBase) {
                signatureKeyName = config.getHMACKey() != null ? config.getHMACKey() : ((AESCryptoProviderBase) provider).getHMACKeyName();
            }

            encryptedVal.put("sig", Base64.encode(provider.getSignature(encryptedValString.getBytes(), signatureKeyName)));
            parent.removeKey(lastPointer);
            parent.put(JsonObject.ENCRYPTION_PREFIX + lastPointer, encryptedVal);
