            try {
                globalServiceLock.writeLock().lock();
                if (globalServices.containsKey(service.type())) {
                    globalServices.remove(service.type());
                }
            } finally {
                globalServiceLock.writeLock().unlock();
