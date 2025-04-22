					RevCommit selectedCommit = (RevCommit) selected;

					RevWalk revWalk = new RevWalk(repository);
					RevCommit reparsedCommit = revWalk.parseCommit(selectedCommit.getId());

					CommitEditor.open(new RepositoryCommit(repository, reparsedCommit));
				} catch (IOException e) {
					Activator.showError("Error opening commit viewer", e); //$NON-NLS-1$
