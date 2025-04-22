    private HashAlgorithm lookupHashAlgorithm(String algorithm) {
        HashAlgorithm ha = HashAlgorithm.NATIVE_HASH;
        if ("crc".equalsIgnoreCase(algorithm)) {
            ha = HashAlgorithm.CRC32_HASH;
        } else if ("fnv1_32".equalsIgnoreCase(algorithm)) {
            ha = HashAlgorithm.FNV1_32_HASH;
        } else if ("fnv1_64".equalsIgnoreCase(algorithm)) {
            ha = HashAlgorithm.FNV1_64_HASH;
        } else if ("fnv1a_32".equalsIgnoreCase(algorithm)) {
            ha = HashAlgorithm.FNV1A_32_HASH;
        } else if ("fnv1a_64".equalsIgnoreCase(algorithm)) {
            ha = HashAlgorithm.FNV1A_64_HASH;
        } else if ("md5".equalsIgnoreCase(algorithm)) {
            ha = HashAlgorithm.KETAMA_HASH;
        } else {
            throw new IllegalArgumentException("Unhandled algorithm type: "
                    + algorithm);
        }
        return ha;
    }

