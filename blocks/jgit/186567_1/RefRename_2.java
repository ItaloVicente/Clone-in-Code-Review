			if (refCache == null) {
				return doRename();
			}

			Lock cacheLock = refCache.getLock().writeLock();
			cacheLock.lock();
			try {
				result = doRename();
				if (result.updateSucceeded()) {
					refCache.onRenamed(source
				}
				return result;
			} finally {
				cacheLock.unlock();
