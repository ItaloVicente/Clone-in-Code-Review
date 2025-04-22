		try {
			ModalContext.run(new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					CommitInfo headCommitInfo = CommitHelper
							.getHeadCommitInfo(repository);
					RevCommit previousCommit = headCommitInfo.getCommit();
