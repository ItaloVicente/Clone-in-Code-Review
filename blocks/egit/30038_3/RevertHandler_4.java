
		try {
			boolean allOnCurrentBranch = true;
			for (RevCommit commit : commits) {
				if (!CommitUtil.isCommitInCurrentBranch(commit, repo)) {
					allOnCurrentBranch = false;
					break;
				}
			}
			if (!allOnCurrentBranch) {
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

