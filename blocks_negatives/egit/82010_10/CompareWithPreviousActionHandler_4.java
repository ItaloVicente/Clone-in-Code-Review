
		Job job = new Job(UIText.CompareUtils_jobName) {

			@Override
			public IStatus run(IProgressMonitor monitor) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				try {
					IWorkbenchPage workBenchPage = HandlerUtil
							.getActiveWorkbenchWindowChecked(event)
							.getActivePage();
					final PreviousCommit previous = getPreviousRevision(event,
							resources[0]);
					if (previous != null) {
						CompareUtils.compare(resources, repository,
								Constants.HEAD, previous.commit.getName(),
								true, workBenchPage);
					}
				} catch (Exception e) {
					Activator.handleError(
							UIText.CompareWithRefAction_errorOnSynchronize, e,
							true);
				}
				return Status.OK_STATUS;
