		List<RevCommit> commits = getSelectedCommits();
		for (RevCommit commit : commits) {
			try {
				CommitEditor.open(new RepositoryCommit(repository, commit));
			} catch (PartInitException e) {
				Activator.showError("Error opening commit viewer", e); //$NON-NLS-1$
			}
