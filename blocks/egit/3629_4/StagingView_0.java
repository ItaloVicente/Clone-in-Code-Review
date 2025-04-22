						asyncExec(new Runnable() {

							public void run() {
								CommitEditor.openQuiet(new RepositoryCommit(
										repository, newCommit));
							}
						});
