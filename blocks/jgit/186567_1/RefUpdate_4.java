					if (refCache == null) {
						return doDelete(status);
					}

					Lock cacheLock = refCache.getLock().writeLock();
					cacheLock.lock();
					try {
						Result res = doDelete(status);
						if (status.updateSucceeded()) {
							refCache.onDeleted(RefUpdate.this
						}
						return res;
					} finally {
						cacheLock.unlock();
