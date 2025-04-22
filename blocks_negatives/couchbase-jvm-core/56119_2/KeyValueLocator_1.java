    /**
     * Calculates the ketama hash for the given key.
     *
     * @param key the key to calculate.
     * @return the calculated hash.
     */
    private static long calculateKetamaHash(final byte[] key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(key);
            byte[] digest = md5.digest();
            long rv = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);
            return rv & 0xffffffffL;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not encode ketama hash.", e);
        }
    }

