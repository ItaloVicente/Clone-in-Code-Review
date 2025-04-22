					try {
						for (Reference<Repository> ref : cache.cacheMap
								.values()) {
							Repository db = ref.get();
							if (db != null && isExpired(db)) {
								RepositoryCache.close(db);
							}
