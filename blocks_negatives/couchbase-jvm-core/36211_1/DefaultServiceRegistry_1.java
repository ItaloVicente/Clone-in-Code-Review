            try {
                localServiceLock.writeLock().lock();
                if (!localServices.containsKey(bucket)) {
                    localServices.put(bucket, new HashMap<ServiceType, Service>());
                }
                if (!localServices.get(bucket).containsKey(service.type())) {
                    localServices.get(bucket).put(service.type(), service);
                }
            } finally {
                localServiceLock.writeLock().unlock();
