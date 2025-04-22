
		@Override
		public Repository getRepository() {
			return repository;
		}

		@Override
		public String getGitPath() {
			return gitPath;
		}

		@Override
		public Source getSource() {
			return Source.WORKING_TREE;
		}

		@Override
		public AnyObjectId getCommitId() {
			return null;
		}
