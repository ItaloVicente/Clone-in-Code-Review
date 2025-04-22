        JceksKeyStoreProvider kp = new JceksKeyStoreProvider("secret");
        String aesKeyName = "mypublickey";
        byte[] secret = "!mysecretkey#9^5usdk39d&dlf)03sL".getBytes(Charset.forName("UTF-8"));
        kp.storeKey(aesKeyName, secret);

        String hmacKeyName = "HMACsecret";
        secret = "myauthpassword".getBytes(Charset.forName("UTF-8"));
        kp.storeKey(hmacKeyName, secret);

        AES256CryptoProvider aes256CryptoProvider = new AES256CryptoProvider(kp, aesKeyName, hmacKeyName);
        encryptionConfig = new EncryptionConfig();
        encryptionConfig.addCryptoProvider(aes256CryptoProvider);
