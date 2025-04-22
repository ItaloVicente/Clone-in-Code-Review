            byte[] encryptedBytes;
            String encryptedValueWithConfig;

            if (object.containsKey("iv")) {
                encryptedValueWithConfig = object.getString("kid") + object.getString("alg")
                        + object.getString("iv") + object.getString("ciphertext");

                byte[] encrypted = Base64.decode(object.getString("ciphertext"));
                byte[] iv = Base64.decode(object.getString("iv"));
                encryptedBytes = new byte[iv.length + encrypted.length];
                System.arraycopy(iv, 0, encryptedBytes, 0, iv.length);
                System.arraycopy(encrypted, 0, encryptedBytes, iv.length, encrypted.length);
            } else {
                encryptedValueWithConfig = object.getString("kid") + object.getString("alg")
                        + object.getString("ciphertext");
                encryptedBytes = Base64.decode(object.getString("ciphertext"));
            }

            byte[] signature = Base64.decode(object.getString("sig"));
            String signatureKey = key;
            if (provider instanceof AESCryptoProviderBase) {
                signatureKey = ((AESCryptoProviderBase) provider).getHMACKeyName();
            }

            if (!provider.verifySignature(encryptedValueWithConfig.getBytes(), signature, signatureKey)) {
                throw new SecurityException("Signature check for data integrity failed");
            }

            byte[] decryptedBytes = provider.decrypt(encryptedBytes);
