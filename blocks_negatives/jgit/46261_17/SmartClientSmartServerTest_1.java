	private final class TestRepoResolver
			implements RepositoryResolver<HttpServletRequest> {

		private final TestRepository<Repository> repo;

		private final String repoName;

		private TestRepoResolver(TestRepository<Repository> repo,
				String repoName) {
			this.repo = repo;
			this.repoName = repoName;
		}

		@Override
		public Repository open(HttpServletRequest req, String name)
				throws RepositoryNotFoundException, ServiceNotEnabledException {
			if (!name.equals(repoName))
				throw new RepositoryNotFoundException(name);

			Repository db = repo.getRepository();
			db.incrementOpen();
			return db;
		}
	}
