
                Object value = parent.get(lastPointer);
                JsonObject encryptedVal = JsonObject.create();
                CryptoProvider provider = this.cryptoManager.getProvider(providerName);
                String jsonValue = JacksonTransformers.MAPPER.writeValueAsString(value);

                encryptedVal.put("kid", provider.getKeyStoreProvider().publicKeyName());
                encryptedVal.put("alg", provider.getProviderName());

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
                byte[] signature = provider.getSignature(encryptedValString.getBytes());

                if (signature != null) {
                    encryptedVal.put("sig", Base64.encode(provider.getSignature(encryptedValString.getBytes())));
                }

                parent.removeKey(lastPointer);
                parent.put(JsonObject.ENCRYPTION_PREFIX + lastPointer, encryptedVal);
