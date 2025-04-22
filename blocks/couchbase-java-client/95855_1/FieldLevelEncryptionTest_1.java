        kp1.storeKey(hmacKeyName, secret);
        kp1.signingKeyName(hmacKeyName);
        kp1.publicKeyName(aesKeyName);

        AES256CryptoProvider aes256CryptoProvider = new AES256CryptoProvider(kp1);
        JceksKeyStoreProvider kp2 = new JceksKeyStoreProvider("secret");
        RSACryptoProvider rsaCryptoProvider = new RSACryptoProvider(kp2);
        kp2.privateKeyName("MyPrivateKeyName");
        kp2.publicKeyName("MyPublicKeyName");

        String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDA/qz+yKr6B5kB\n" +
                                "w3znxqzVTpUgV40OO96mXzyPJ3vjyJ0O3VqEOrDukbDlYzKCrThqdZAbeQpRSESX\n" +
                                "HAb0En/PivElc7RVt+W/TCGAGVNVXFe5dI/C3IDbTQIQ0s7ppNpU39p621K8DdwS\n" +
                                "5OK3p6DgDKLSJI73tIj/0mnhd4jhsDkuznnVj1dH49Vudo2ANbEa95unD0a+N4r+\n" +
                                "FR0wfA/xCEt3IsuNtDqLdjE5YVhaqO1JBq8J2Skc26LtTWtQvFhgVICFHrh07ChO\n" +
                                "zbIHyUsxCyNN0hBHqhBmBopGVXM/MeUmCnS0hInG8lhety3tkSMQW1Muc4zHgTsx\n" +
                                "uZw3duwPAgMBAAECggEAaKKrkIeji2PLJRWkBtXEpvGwEJTnOSxkjrdb0hGKLfl6\n" +
                                "jbCdfsuDWhVLX1Lk88yOpcmPlBWP7nnMFlFvw6yz9wZRsAiHYWIPAiR4lUcl00X5\n" +
                                "mecEepWqlzutPwnMfQiQByxG/A0lUigBhYzrDr+njVHMhTqk+M+851ZhaYixggpk\n" +
                                "CFWdN/xBcMGkF2POuuVOehwKE20R2SrIIWYUgs5ZSIVvNawcl7n/Wj6/4rDipAYc\n" +
                                "1lWkWLa9AInFMt0RtxQcKZWqBA6z7WGz4DHfumRd9UFGH3sTlFXQ6zwm0feybD24\n" +
                                "JxC94lqea49WGxr7zOM4raPUAeN2xo9mAw/C6V5WgQKBgQDmYnvaU9FwWM1NMNB8\n" +
                                "tQfEKritZ6SRL5CyA/IG4TzPmaLtKwe5JxWL49wt6FFjx7pnjrgqX02LpzXsdwz4\n" +
                                "GbxrLvSHE95OWqtUGPEqJOLEXUFxxsEc+N0mXRYIQ4PEoynlMBqwmx8zs+DXxxg1\n" +
                                "qSY6or9qBzssm2zKj32Mp8VhHwKBgQDWc/OYZ+FlnYlew0E2WrK+K1XtTPBrrYMw\n" +
                                "YlS6iw4OdrPm/iBM9mEwXiBo7CHfCh8/zzYlkzVgQMXrg2fe0wNciaJ2gT20oMw/\n" +
                                "JPX6htoEQ97wB+EAkZ8ytYeBTdiJx3XpvEq9tTeQ4OkWAG2cTnQlxx4TIVhNbNl4\n" +
                                "1FaWxbhnEQKBgQCpPEMa2GOLkdAOGgOs+BaiZXeP+giLllNGUVui7iYLoiJq8icU\n" +
                                "Pb+4KUP+fR/8miU2GULz7Vo7cjNMZw+h2NXuLmn2KAQvrq8YcdIGUV47PP3sJEKL\n" +
                                "k8xweATNQTs0YV9POo0AmpLLGiHaoCgKkxzACfluW61+URYTnmBtyHhXpQKBgQCs\n" +
                                "WIFTYWDOZl3o72hwQ1HU7UTgMe4hy09cShon1OsWCqWoJWFWGMegtHS9fc/2zM6y\n" +
                                "XFf6uKSz1zp4fKG0fMb9zornTBSIHpYmxRB+J3P864K2Ss6zw1Q6z5K4AxTcHZWQ\n" +
                                "o8c5UPL4Fxibmvp8HLzRQ4XTAABUMP9RUOzJvNrm0QKBgH6SxmJRtn7xZNdUgHcc\n" +
                                "klPxTxtnzrvqWtFQnljEH5q8eHiD9Mj57neNkoFW8HcyGnpAUxkhO5RNGCIa4CMF\n" +
                                "qnhJnfY+bK7dPGJYPEqS2a8Uw8tRUWe0MnS2Ha71enDz5PTUA4IVzQ8q0LyDQKi/\n" +
                                "G76pYt+qcmdMLH5DTf51CP2t";
        String pubKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwP6s/siq+geZAcN858as1U6VIFeNDj" +
                "vepl88jyd748idDt1ahDqw7pGw5WMygq04anWQG3kKUUhElxwG9BJ/z4rxJXO0Vbflv0whgBlTVVxXuXSPwty" +
                "A200CENLO6aTaVN/aettSvA3cEuTit6eg4Ayi0iSO97SI/9Jp4XeI4bA5Ls551Y9XR+PVbnaNgDWxGvebpw9Gv" +
                "jeK/hUdMHwP8QhLdyLLjbQ6i3YxOWFYWqjtSQavCdkpHNui7U1rULxYYFSAhR64dOwoTs2yB8lLMQsjTdIQR6oQZgaKRlVzPzHlJgp0tISJxvJYXrct7ZEjEFtTLnOMx4E7MbmcN3bsDwIDAQAB";

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec spec = new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(pubKeyStr));
        PublicKey publicKey = keyFactory.generatePublic(spec);
