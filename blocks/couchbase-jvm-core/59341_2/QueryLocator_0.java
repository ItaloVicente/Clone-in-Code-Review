        int nodeSize = nodes.size();
        int offset = (int) counter++ % nodeSize;
        for (int i = 0; i < nodeSize; i++) {
            int idx = i + offset;
            if (idx == nodeSize) {
                offset = 0;
                idx = i;
            }
            Node node = nodes.get(idx);
            if (node.serviceEnabled(ServiceType.QUERY)) {
