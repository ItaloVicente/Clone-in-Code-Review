				ValidationUtils
						.getRefNameInputValidator(repo, Constants.R_TAGS),
				currentBranchName);

		RevWalk revCommits = getRevCommits(event);
		dialog.setRevCommitList(revCommits);

		List<RevTag> tags = getRevTags(event);
		dialog.setExistingTags(tags);
