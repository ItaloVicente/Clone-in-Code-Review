		}

		Runnable terminator = new Runnable() {

			@Override
			public void run() {
				try {
					for (Reference<Repository> ref : cache.cacheMap
							.values()) {
						Repository db = ref.get();
						if (db != null && isExpired(db)) {
							RepositoryCache.close(db);
						}
					}
				} catch (Throwable e) {
					LOG.error(e.getMessage()
				}
			}

			private boolean isExpired(Repository db) {
				return db.useCnt.get() == 0 && (System.currentTimeMillis()
						- db.closedAt.get() > 20000);
			}
		};

		WorkQueue.getExecutor().scheduleWithFixedDelay(terminator
				TimeUnit.SECONDS);
