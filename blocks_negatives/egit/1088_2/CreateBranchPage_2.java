		Ref sourceBranch;
		if (myBaseBranch != null) {
			sourceBranch = myBaseBranch;
		} else {
			sourceBranch = myRepository.getRef(getSourceBranchName());
		}
		ObjectId startAt = sourceBranch.getObjectId();
		String startBranch = myRepository
				.shortenRefName(sourceBranch.getName());
