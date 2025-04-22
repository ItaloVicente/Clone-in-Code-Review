				CompletableFuture<Optional<Duration>> f = CompletableFuture
						.supplyAsync(() -> {
							Lock lock = locks.computeIfAbsent(s
									l -> new ReentrantLock());
							if (!lock.tryLock()) {
								return Optional.empty();
							}
							Optional<Duration> resolution;
							try {
								resolution = measureFsTimestampResolution(s
								if (resolution.isPresent()) {
									FileStoreAttributeCache cache = new FileStoreAttributeCache(
											resolution.get());
									attributeCache.put(s
									if (LOG.isDebugEnabled()) {
										LOG.debug(cache.toString());
									}
								}
							} finally {
								lock.unlock();
								locks.remove(s);
							}
							return resolution;
						});
				Optional<Duration> d = f.get(background.get() ? 50 : 5000
						TimeUnit.MILLISECONDS);
				if (d.isPresent()) {
					return d.get();
