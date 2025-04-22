				if (c != null) {
					return c.getFsTimestampResolution();
				}
				Lock lock = locks.computeIfAbsent(s
				if (lock.tryLock()) {
					try {
						CompletableFuture<FileStoreAttributeCache> f = CompletableFuture
								.supplyAsync(() -> new FileStoreAttributeCache(
										s
						f.exceptionally(e -> {
							Throwable cause = e.getCause();
							if (!(cause instanceof NoSuchFileException)) {
								LOG.error(e.getLocalizedMessage()
							}
							return null;
						});
						f.thenAccept(cache -> {
							if (cache != null) {
								attributeCache.put(s
							}
						});
						if (background.get()) {
							if (LOG.isDebugEnabled()) {
								f.thenAccept(
										cache -> LOG.debug(cache.toString()));
							}
						} else {
							FileStoreAttributeCache cache = f.get();
							if (LOG.isDebugEnabled()) {
								LOG.debug(cache.toString());
							}
							return cache.getFsTimestampResolution();
						}
					} finally {
						lock.unlock();
						locks.remove(s);
