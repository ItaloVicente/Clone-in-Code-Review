					if (refCache == null) {
						return doUpdate(status);
					}

					Lock cacheLock = refCache.getLock().writeLock();
					cacheLock.lock();
					try {
						Result res = doUpdate(status);
						if (status.updateSucceeded()) {
							refCache.onUpdated(RefUpdate.this
						}
						return res;
					} finally {
						cacheLock.unlock();
					}
