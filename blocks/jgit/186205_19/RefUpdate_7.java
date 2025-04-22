					}
					if (!refCache.isPresent()) {
						return doUpdate(status);
					}

					Lock cacheLock = refCache.get().getLock().writeLock();
					cacheLock.lock();
					try {
						Result res = doUpdate(status);
						if (status.updateSucceeded()) {
							refCache.get().insert(getUpdatedRef());
						}
						return res;
					} finally {
						cacheLock.unlock();
					}
