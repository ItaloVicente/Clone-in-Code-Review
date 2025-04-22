            String signatureKeyName = keyName;
            if (provider instanceof AESCryptoProviderBase) {
                signatureKeyName = config.getHMACKey() != null ? config.getHMACKey() : ((AESCryptoProviderBase) provider).getHMACKeyName();
            }

            encryptedVal.put("sig", Base64.encode(provider.getSignature(encryptedValString.getBytes(), signatureKeyName)));
