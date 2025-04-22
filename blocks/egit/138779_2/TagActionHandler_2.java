			dialog = new CreateTagDialog(getShell(event), currentBranchName,
					repo);
		} else {
			repo = commit.getRepository();
			dialog = new CreateTagDialog(getShell(event),
					commit.getRevCommit().getId(), repo);
