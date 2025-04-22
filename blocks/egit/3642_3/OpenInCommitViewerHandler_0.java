					RevCommit selectedCommit = (RevCommit) selected;

					RevCommit reparsedCommit = revWalk.parseCommit(selectedCommit.getId());

					CommitEditor.open(new RepositoryCommit(repository, reparsedCommit));
				} catch (IOException e) {
					Activator.showError("Error opening commit viewer", e); //$NON-NLS-1$
