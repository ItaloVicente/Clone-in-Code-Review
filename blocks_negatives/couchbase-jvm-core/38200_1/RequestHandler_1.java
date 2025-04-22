        try {
            nodeLock.readLock().lock();
            if (nodes.contains(node)) {
                return Observable.from(node.state());
            }
        } finally {
            nodeLock.readLock().unlock();
