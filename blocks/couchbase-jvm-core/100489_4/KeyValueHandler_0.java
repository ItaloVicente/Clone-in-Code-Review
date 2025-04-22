
    private static byte[] getKey(BinaryRequest req) {
        boolean enableCollection = Boolean.parseBoolean(System.getProperty("com.couchbase.enableCollections", "false"));
        if (enableCollection && req.collectionId() <= Math.pow(2,31)) {
            byte[] cid = UnsignedLEB128.encode(req.collectionId());
            byte[] suffix = req.keyBytes();
            byte[] keyWithCollectionPrefix = new byte[cid.length+suffix.length];
            System.arraycopy(cid, 0, keyWithCollectionPrefix, 0, cid.length);
            System.arraycopy(suffix, 0, keyWithCollectionPrefix, cid.length, suffix.length);
            return keyWithCollectionPrefix;
        } else {
            return req.keyBytes();
        }
    }
