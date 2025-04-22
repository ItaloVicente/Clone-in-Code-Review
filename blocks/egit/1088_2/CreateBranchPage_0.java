		String newRefName = getBranchName();

		RefUpdate updateRef = myRepository.updateRef(newRefName);
		ObjectId startAt = new RevWalk(myRepository).parseCommit(myRepository
				.resolve(getSourceBranchName()));

