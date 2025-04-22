
		Runnable terminator = new Runnable() {

			@Override
			public void run() {
				try {
					for (Reference<Repository> ref : cache.cacheMap.values()) {
						Repository repository = ref.get();
						if (repository != null) {
							if (repository.useCnt.get() == 0
									&& (System.currentTimeMillis() - repository.closedAt.get() > 20000)) {
								RepositoryCache.close(repository);
							}
						}
					}
				} catch (Throwable e) {
					LOG.error(e.getMessage()
				}
			}
		};

		AlarmQueue.getExecutor().scheduleWithFixedDelay(terminator
				TimeUnit.SECONDS);
