		ObjectId startAt;
		if (commitMode)
			startAt = myBaseCommit.getId();
		else
			startAt = new RevWalk(myRepository).parseCommit(myRepository
					.resolve(getSourceBranchName()));
