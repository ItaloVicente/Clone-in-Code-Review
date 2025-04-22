		} else if (newCount == -1) {
			LOG.warn(JGitText.get().corruptUseCnt);
			if (LOG.isDebugEnabled()) {
				IllegalStateException e = new IllegalStateException();
				LOG.debug(""
			}
			if (RepositoryCache.isCached(this)) {
				closedAt.set(System.currentTimeMillis());
			}
