
        long hash = calculateKetamaHash(request.keyBytes());
        if (!config.ketamaNodes().containsKey(hash)) {
            SortedMap<Long, NodeInfo> tailMap = config.ketamaNodes().tailMap(hash);
            if (tailMap.isEmpty()) {
                hash = config.ketamaNodes().firstKey();
            } else {
                hash = tailMap.firstKey();
            }
        }

        NodeInfo found = config.ketamaNodes().get(hash);
