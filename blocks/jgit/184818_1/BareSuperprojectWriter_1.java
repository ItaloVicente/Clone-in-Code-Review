		StringBuilder commitMsg = new StringBuilder(
				RepoText.get().repoCommitMessage);
		if (commitBody != null) {
			commitMsg.append(commitBody);
		}
		commit.setMessage(commitMsg.toString());
