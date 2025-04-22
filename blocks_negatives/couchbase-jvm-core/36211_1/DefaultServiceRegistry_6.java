            try {
                globalServiceLock.readLock().lock();
                return globalServices.get(type);
            } finally {
                globalServiceLock.readLock().unlock();
            }
