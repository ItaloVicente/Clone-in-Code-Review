	private void openCommit() {
		try {
			getShell().dispose();
			CommitEditor.open(new RepositoryCommit(revision.getRepository(),
					revision.getCommit()));
		} catch (PartInitException pie) {
			Activator.logError(pie.getLocalizedMessage(), pie);
		}
	}

	private void showCommitInHistory() {
		getShell().dispose();
		IHistoryView part = (IHistoryView) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findView(IHistoryView.VIEW_ID);
		if (part == null)
			return;

		Repository repository = revision.getRepository();
		if (!repository.isBare()) {
			String sourcePath = revision.getSourcePath();
			File file = new File(repository.getWorkTree(), sourcePath);
			BlameHistoryPageInput input = new BlameHistoryPageInput(repository,
					revision.getCommit(), file);
			part.showHistoryFor(input);
		} else {
			HistoryPageInput input = new BlameHistoryPageInput(repository,
					revision.getCommit());
			part.showHistoryFor(input);
		}
	}

