		try {
			new ProgressMonitorDialog(getShell()).run(true, false,
					new IRunnableWithProgress() {

						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							CommitInfo headCommitInfo = CommitHelper
									.getHeadCommitInfo(repository);
							RevCommit previousCommit = headCommitInfo
									.getCommit();

							amendingCommitInRemoteBranch = isContainedInAnyRemoteBranch(previousCommit);
							previousCommitMessage = headCommitInfo
									.getCommitMessage();
							previousAuthor = headCommitInfo.getAuthor();
						}
					});
		} catch (InvocationTargetException e) {
			org.eclipse.egit.ui.Activator.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			org.eclipse.egit.ui.Activator.error(e.getMessage(), e);
		}
