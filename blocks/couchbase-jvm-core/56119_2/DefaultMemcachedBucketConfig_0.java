    @Override
    public InetAddress nodeForId(final byte[] id) {
        long hash = calculateKetamaHash(id);

        if (!ketamaNodes.containsKey(hash)) {
            SortedMap<Long, NodeInfo> tailMap = ketamaNodes.tailMap(hash);
            if (tailMap.isEmpty()) {
                hash = ketamaNodes.firstKey();
            } else {
                hash = tailMap.firstKey();
            }
        }

        return ketamaNodes.get(hash).hostname();
    }

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

