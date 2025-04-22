				.getShell(), ValidationUtils.getRefNameInputValidator(repo,
				Constants.R_TAGS), currentBranchName);

		RevWalk revCommits = getRevCommits(repo);
		dialog.setRevCommitList(revCommits);

		List<RevTag> tags = getRevTags(repo);
		dialog.setExistingTags(tags);
