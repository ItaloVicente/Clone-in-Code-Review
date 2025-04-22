					commitMessageComponent.getCommitMessage()) {

				protected RevCommit commit() throws TeamException {
					RevCommit commit = super.commit();
					openNewCommit(commit);
					return commit;
				}

				protected RevCommit commitAll(Date commitDate,
						TimeZone timeZone, PersonIdent authorIdent,
						PersonIdent committerIdent) throws TeamException {
					RevCommit commit = super.commitAll(commitDate, timeZone,
							authorIdent, committerIdent);
					openNewCommit(commit);
					return commit;
				}

				private void openNewCommit(final RevCommit newCommit) {
					if (newCommit != null && openNewCommitsAction.isChecked())
						PlatformUI.getWorkbench().getDisplay()
								.asyncExec(new Runnable() {

									public void run() {
										CommitEditor
												.openQuiet(new RepositoryCommit(
														repository, newCommit));
									}
								});
				}

			};
