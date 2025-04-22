		@Override
		public AnyObjectId getCommitId() {
			return null;
		}

		@Override
		public GitItemState getGitState() {
			return GitItemStateFactory.getInstance().get(resource);
		}
