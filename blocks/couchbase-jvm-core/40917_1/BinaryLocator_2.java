        final MemcachedBucketConfig config) {

        long hash = ketamaHash(request.key());
        if (!config.ketamaNodes().containsKey(hash)) {
            SortedMap<Long, NodeInfo> tailMap = config.ketamaNodes().tailMap(hash);
            if (tailMap.isEmpty()) {
                hash = config.ketamaNodes().firstKey();
            } else {
                hash = tailMap.firstKey();
            }
        }

        NodeInfo found = config.ketamaNodes().get(hash);
        request.partition((short) 0);
        for (Node node : nodes) {
            if (node.hostname().equals(found.hostname())) {
                return new Node[] { node };
            }
        }

        throw new IllegalStateException("Node not found for request" + request);
    }

    private long ketamaHash(final String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(key.getBytes(CharsetUtil.UTF_8));
            byte[] digest = md5.digest();
            long rv = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);
            return rv & 0xffffffffL;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not encode ketama hash.", e);
        }
