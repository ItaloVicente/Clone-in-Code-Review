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
