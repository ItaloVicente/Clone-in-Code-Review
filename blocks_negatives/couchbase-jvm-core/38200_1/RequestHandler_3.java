        try {
            nodeLock.readLock().lock();
            for (Node node : nodes) {
                if (node.hostname().equals(hostname)) {
                    return node;
                }
