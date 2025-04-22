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
