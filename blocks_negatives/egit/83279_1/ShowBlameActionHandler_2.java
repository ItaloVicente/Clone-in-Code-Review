	private static class Data {
		private final Repository repository;
		private final String repositoryRelativePath;
		private final IStorage storage;
		private final RevCommit startCommit;

		public Data(Repository repository, String repositoryRelativePath,
				IStorage storage, RevCommit startCommit) {
			this.repository = repository;
			this.repositoryRelativePath = repositoryRelativePath;
			this.storage = storage;
			this.startCommit = startCommit;
