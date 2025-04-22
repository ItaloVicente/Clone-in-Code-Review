	public TagOperation(Repository repo, TagBuilder tag,
			boolean shouldMoveTag) {
		this.tag = tag;
		this.repo = repo;
		this.shouldMoveTag = shouldMoveTag;
		this.annotated = true;
	}
