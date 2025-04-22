	public TagNode(RepositoryTreeNode parent, Repository repository, Ref ref,
			boolean annotated, String commitId, String commitShortMessage) {
		super(parent, RepositoryTreeNodeType.TAG, repository, ref);
		this.annotated = annotated;
		this.commitId = commitId;
		this.shortMessage = commitShortMessage;
	}

	public boolean isAnnotated() {
		return annotated;
	}

	public String getCommitId() {
		return commitId;
	}

	public String getCommitShortMessage() {
		return shortMessage;
	}
