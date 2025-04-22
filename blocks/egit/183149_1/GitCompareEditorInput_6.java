	@Override
	protected GitInfo getGitInfo(IPath path) {
		return new GitInfo() {

			@Override
			public Repository getRepository() {
				return GitCompareEditorInput.this.getRepository();
			}

			@Override
			public String getGitPath() {
				return path.toString();
			}

			@Override
			public Source getSource() {
				return leftVersion == null ? Source.WORKING_TREE
						: Source.COMMIT;
			}

			@Override
			public AnyObjectId getCommitId() {
				return null;
			}
		};
	}

