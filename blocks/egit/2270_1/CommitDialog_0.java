		if(commitMessage != "") { //$NON-NLS-1$
			return commitMessage;
		}

		if (amending) {
			return previousCommitMessage;
		}

