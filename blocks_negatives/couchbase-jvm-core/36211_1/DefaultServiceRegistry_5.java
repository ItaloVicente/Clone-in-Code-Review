            try {
                localServiceLock.readLock().lock();
                return localServices.get(request.bucket()).get(type);
            } finally {
                localServiceLock.readLock().unlock();
            }
