			}
		}
	}

	private synchronized void prune(Set<String> configuredRepositories) {
		Iterator<File> iterator = repositoryCache.keySet().iterator();
		while (iterator.hasNext()) {
			File gitDir = iterator.next();
			if (!configuredRepositories.contains(gitDir.getAbsolutePath())) {
				iterator.remove();
			}
