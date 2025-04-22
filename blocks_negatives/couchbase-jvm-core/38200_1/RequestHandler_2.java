                try {
                    nodeLock.writeLock().lock();
                    nodes.add(node);
                } finally {
                    nodeLock.writeLock().unlock();
                }
