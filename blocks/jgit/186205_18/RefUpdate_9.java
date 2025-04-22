			Lock cacheLock = refCache.get().getLock().writeLock();
			cacheLock.lock();
			try {
				result = doLink(target);
				if (result.updateSucceeded()) {
					refCache.get().insert(getUpdatedRef());
				}
				return result;
			} finally {
				cacheLock.unlock();
			}
