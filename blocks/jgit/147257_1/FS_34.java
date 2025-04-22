				} else {
					LOG.debug(
							"{}: cannot measure timestamp resolution of unborn directory {}"
							Thread.currentThread()
					return FALLBACK_FILESTORE_ATTRIBUTES;
				}

				CompletableFuture<Optional<FileStoreAttributes>> f = CompletableFuture
						.supplyAsync(() -> {
							Lock lock = locks.computeIfAbsent(s
									l -> new ReentrantLock());
							if (!lock.tryLock()) {
								LOG.debug(
										"{}: couldn't get lock to measure timestamp resolution in {}"
										Thread.currentThread()
								return Optional.empty();
							}
							Optional<FileStoreAttributes> attributes = Optional
									.empty();
							try {
								FileStoreAttributes c = attributeCache
										.get(s);
								if (c != null) {
									return Optional.of(c);
								}
								attributes = readFromConfig(s);
								if (attributes.isPresent()) {
									attributeCache.put(s
									return attributes;
								}

								Optional<Duration> resolution = measureFsTimestampResolution(
										s
								if (resolution.isPresent()) {
									c = new FileStoreAttributes(
											resolution.get());
									attributeCache.put(s
									if (c.fsTimestampResolution
											.toNanos() < 100_000_000L) {
										c.minimalRacyInterval = measureMinimalRacyInterval(
												dir);
									}
									if (LOG.isDebugEnabled()) {
										LOG.debug(c.toString());
									}
									saveToConfig(s
								}
								attributes = Optional.of(c);
							} finally {
								lock.unlock();
								locks.remove(s);
							}
							return attributes;
						});
				f.exceptionally(e -> {
					LOG.error(e.getLocalizedMessage()
					return Optional.empty();
				});
				Optional<FileStoreAttributes> d = background.get() ? f.get(
						100
				if (d.isPresent()) {
					return d.get();
