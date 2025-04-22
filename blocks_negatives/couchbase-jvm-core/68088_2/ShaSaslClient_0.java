        final Mac mac;

        try {
            mac = Mac.getInstance(hmacAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }

        final SecretKeySpec secretKey = new SecretKeySpec(key, mac.getAlgorithm());
