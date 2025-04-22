            try {
                localServiceLock.writeLock().lock();
                if (localServices.containsKey(bucket) && localServices.get(bucket).containsKey(service.type())) {
                    localServices.get(bucket).remove(service.type());
                }
                if (localServices.get(bucket).isEmpty()) {
                    localServices.remove(bucket);
                }
            } finally {
                localServiceLock.writeLock().unlock();
