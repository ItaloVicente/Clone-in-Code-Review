	@Override
	protected GitInfo getGitInfo(IPath path) {
		return new GitInfo() {

			@Override
			public Repository getRepository() {
				return GitMergeEditorInput.this.getRepository();
			}

			@Override
			public String getGitPath() {
				return path.toString();
			}

			@Override
			public Source getSource() {
				return Source.WORKING_TREE;
			}

			@Override
			public AnyObjectId getCommitId() {
				return null;
			}
		};
	}

