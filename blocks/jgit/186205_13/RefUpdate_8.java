					if (!refCache.isPresent()) {
						return doDelete(status);
					}

					Lock cacheLock = refCache.get().getLock().writeLock();
					cacheLock.lock();
					try {
						Result res = doDelete(status);
						if (status.updateSucceeded()) {
							refCache.get().delete(RefUpdate.this.getName());
						}
						return res;
					} finally {
						cacheLock.unlock();
					}
