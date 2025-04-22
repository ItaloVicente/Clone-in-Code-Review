
    private static byte[] getKey(BinaryRequest req) {
        byte[] key = null;
        if (req.collectionId() != -1) {
            byte[] cid = UnsignedLEB128.encode(req.collectionId());
            byte[] suffix = req.keyBytes();
            key = new byte[cid.length+suffix.length];
            System.arraycopy(cid, 0, key, 0, cid.length);
            System.arraycopy(key, 0, key, cid.length, suffix.length);
        } else {
            key = req.keyBytes();
        }
        return key;
    }
