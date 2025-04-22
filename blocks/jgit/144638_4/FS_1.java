				Lock lock = locks.computeIfAbsent(s
				CompletableFuture<Optional<Duration>> f = CompletableFuture
						.supplyAsync(() -> {
							if (!lock.tryLock()) {
								return Optional.empty();
							}
							Optional<Duration> resolution;
							try {
								resolution = measureTimestampResolution(s
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
							}
							return resolution;
						});

				if (!background.get()) {
					Optional<Duration> d = f.get();
					if (d.isPresent()) {
						return d.get();
					}
				}
			} catch (IOException | InterruptedException
					| ExecutionException e) {
				LOG.error(e.getMessage()
