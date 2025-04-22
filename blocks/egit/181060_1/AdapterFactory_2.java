		return new Class<?>[] { Repository.class, GitInfo.class };
	}

	private static class GitAccessor implements GitInfo {

		@NonNull
		private final IResource resource;

		GitAccessor(@NonNull IResource resource) {
			this.resource = resource;
		}

		@Override
		public Repository getRepository() {
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
			return mapping != null ? mapping.getRepository() : null;
		}

		@Override
		public String getGitPath() {
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
			return mapping != null ? mapping.getRepoRelativePath(resource)
					: null;
		}

		@Override
		public Source getSource() {
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
			return mapping == null ? null : Source.WORKING_TREE;
		}

		@Override
		public AnyObjectId getCommitId() {
			return null;
		}

		@Override
		public GitItemState getGitState() {
			return GitItemStateFactory.getInstance().get(resource);
		}
