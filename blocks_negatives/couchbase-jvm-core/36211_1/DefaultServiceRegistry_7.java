            try {
                localServiceLock.readLock().lock();
                return localServices.get(bucket).get(type);
            } finally {
                localServiceLock.readLock().unlock();
            }
