
		@Override
		public RepositoryHandle build() throws IOException {
			setup();
			File gitDir = getGitDir();
			RepositoryHandle result = null;
			boolean removeCache = false;
			try {
				synchronized (repositoryCache) {
					Reference<RepositoryHandle> r = repositoryCache.get(gitDir);
					if (r != null) {
						RepositoryHandle cached = r.get();
						if (cached != null && cached.getDirectory().exists()) {
							return cached;
						} else {
							Closer.closeReference(
									repositoryCache.remove(gitDir));
							removeCache = true;
						}
					}
					CachingRepository inner = createRepository();
					result = new RepositoryHandle(inner);
					repositoryCache.put(gitDir,
							new RepositoryReference(result, inner, queue));
				}
			} finally {
				if (removeCache) {
					IndexDiffCache indexCache = Activator.getDefault()
							.getIndexDiffCache();
					if (indexCache != null) {
						indexCache.remove(gitDir);
					}
				}
			}
			return result;
		}
	}

	public BaseRepositoryBuilder<? extends BaseRepositoryBuilder, ? extends Repository> getBuilder(
			boolean preventClose, boolean cache) {
		return new Builder() {

			@Override
			public RepositoryHandle build() throws IOException {
				RepositoryHandle result = super.build();
				if (preventClose) {
					result.incrementOpen();
				}
				if (cache) {
					Repository inner = result.getDelegate();
					if (inner instanceof CachingRepository) {
						((CachingRepository) inner).cacheConfig(true);
					}
				}
				return result;
			}
		};
