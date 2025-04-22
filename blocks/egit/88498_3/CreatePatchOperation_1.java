	private AnyObjectId getParentId() {
		RevCommit[] parents = commit.getParents();
		if (parents.length > 1) {
			throw new IllegalStateException(
					CoreText.CreatePatchOperation_cannotCreatePatchForMergeCommit);
		}
