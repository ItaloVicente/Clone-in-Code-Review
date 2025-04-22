	private class Builder extends BaseRepositoryBuilder<Builder, Repository> {

		@Override
		public Repository build() throws IOException {
			CachingRepository repo = new CachingRepository(setup());
			if (isMustExist()) {
				@SuppressWarnings("restriction")
				boolean exists = repo.getObjectDatabase().exists();
				if (!exists) {
					throw new RepositoryNotFoundException(getGitDir());
				}
			}
			return repo;
		}
	}

