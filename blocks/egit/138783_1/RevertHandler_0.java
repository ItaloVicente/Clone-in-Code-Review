		if (repo.isBare()) {
			return null;
		}
		if (repoCommits.stream().anyMatch(c -> repo != c.getRepository())) {
			return null;
		}

		List<RevCommit> commits = repoCommits.stream()
				.map(c -> c.getRevCommit()).collect(Collectors.toList());

		try {
			if (!CommitUtil.areCommitsInCurrentBranch(commits, repo)) {
				MessageDialog.openError(
						HandlerUtil.getActiveShellChecked(event),
						UIText.RevertHandler_Error_Title,
						UIText.RevertHandler_CommitsNotOnCurrentBranch);
				return null;
			}
		} catch (IOException e) {
			throw new ExecutionException(
					UIText.RevertHandler_ErrorCheckingIfCommitsAreOnCurrentBranch,
					e);
		}

		BasicConfigurationDialog.show(repo);
