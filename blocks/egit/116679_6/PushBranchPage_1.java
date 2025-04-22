		private final String localBranchName;

		public FutureRefs(Repository repository, String uriText,
				String localBranchName) {
			super(repository, uriText);
			this.localBranchName = localBranchName;
