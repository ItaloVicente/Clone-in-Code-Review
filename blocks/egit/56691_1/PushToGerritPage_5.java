		String lastBranch = null;
		if (!RepositoryUtil.isDetachedHead(repository)) {
			try {
				lastBranch = repository.getBranch();
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
