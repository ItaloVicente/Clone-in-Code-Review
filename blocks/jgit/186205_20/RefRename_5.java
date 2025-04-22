			if (!refCache.isPresent()) {
				return doRename();
			}

			Lock cacheLock = refCache.get().getLock().writeLock();
			cacheLock.lock();
			try {
				result = doRename();
				if (result.updateSucceeded()) {
					refCache.get().rename(source.getRef()
							destination.getUpdatedRef());
				}
				return result;
			} finally {
				cacheLock.unlock();
			}
