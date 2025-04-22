		if (repository != null) {
			RevWalk revWalk = new RevWalk(repository);
			for (Object selected : getSelection(getPage()).toList())
				try {
					RevCommit selectedCommit = (RevCommit) selected;

					RevCommit reparsedCommit = revWalk.parseCommit(selectedCommit.getId());

					CommitEditor.open(new RepositoryCommit(repository, reparsedCommit));
				} catch (IOException e) {
					Activator.showError("Error opening commit viewer", e); //$NON-NLS-1$
				} catch (PartInitException e) {
					Activator.showError("Error opening commit viewer", e); //$NON-NLS-1$
				}
