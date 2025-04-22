								event -> {
									updateCommitAuthorAndCommitter(repository);
									asyncExec(() -> {
										currentPushMode.clear();
										updateCommitAndPush(repository);
									});
								});
