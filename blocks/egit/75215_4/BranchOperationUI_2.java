	private String confirmTarget(IProgressMonitor monitor) {
		if (target != null) {
			if (!repository.getRepositoryState().canCheckout()) {
				PlatformUI.getWorkbench().getDisplay()
						.asyncExec(new Runnable() {
							@Override
							public void run() {
								MessageDialog.openError(getShell(),
										UIText.BranchAction_cannotCheckout,
										NLS.bind(
												UIText.BranchAction_repositoryState,
												repository.getRepositoryState()
														.getDescription()));
							}
						});
				return null;
			}

			if (shouldCancelBecauseOfRunningLaunches(monitor)) {
				return null;
			}

			askForTargetIfNecessary();
		}
		return target;
	}

	private BranchOperation getOperation(boolean restore) {
		BranchOperation bop = new BranchOperation(repository, target, !restore);
		if (restore) {
			final BranchProjectTracker tracker = new BranchProjectTracker(
					repository);
			final AtomicReference<IMemento> memento = new AtomicReference<>();
			bop.addPreExecuteTask(new PreExecuteTask() {

				@Override
				public void preExecute(Repository pRepo,
						IProgressMonitor pMonitor) throws CoreException {
					memento.set(tracker.snapshot());
				}
			});
			bop.addPostExecuteTask(new PostExecuteTask() {

				@Override
				public void postExecute(Repository pRepo,
						IProgressMonitor pMonitor) throws CoreException {
					IMemento snapshot = memento.get();
					if (snapshot != null) {
						tracker.save(snapshot).restore(pMonitor);
					}
				}
			});
		}
		return bop;
