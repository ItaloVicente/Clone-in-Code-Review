		if(commitMessage != null) {
			return commitMessage;
		}

		if (amending)
			return previousCommitMessage;

