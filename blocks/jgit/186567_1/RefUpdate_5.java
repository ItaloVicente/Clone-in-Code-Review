			if (refCache == null) {
				return doLink(target);
			}

			Lock cacheLock = refCache.getLock().writeLock();
			cacheLock.lock();
			try {
				result = doLink(target);
				if (result.updateSucceeded()) {
					refCache.onLinked(this
				}
				return result;
			} finally {
				cacheLock.unlock();
