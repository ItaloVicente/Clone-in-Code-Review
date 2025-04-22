            try {
                globalServiceLock.writeLock().lock();
                if (!globalServices.containsKey(service.type())) {
                    globalServices.put(service.type(), service);
                }
            } finally {
                globalServiceLock.writeLock().unlock();
