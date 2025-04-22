        PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(privateKeyStr));
        PrivateKey privateKey = keyFactory.generatePrivate(privSpec);

        kp2.storeKey("MyPrivateKeyName", privateKey.getEncoded());
        kp2.storeKey("MyPublicKeyName", publicKey.getEncoded());

        cryptoManager = new CryptoManager();
        cryptoManager.registerProvider("AES", aes256CryptoProvider);
        cryptoManager.registerProvider("RSA", rsaCryptoProvider);
