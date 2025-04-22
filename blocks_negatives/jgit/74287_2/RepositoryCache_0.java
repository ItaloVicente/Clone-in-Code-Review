
		Runnable terminator = new Runnable() {
			@Override
			public void run() {
				try {
					for (Reference<Repository> ref : cache.cacheMap.values()) {
						Repository repository = ref.get();
						if (repository != null) {
							cache.evict(repository);
						}
					}
				} catch (Throwable e) {
					LOG.error(e.getMessage(), e);
				}
			}
		};
