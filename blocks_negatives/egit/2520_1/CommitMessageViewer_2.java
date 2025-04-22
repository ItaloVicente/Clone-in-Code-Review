
		if (!currentDiffs.isEmpty() && commit.getParentCount() == 1)
			try {
				PlatformUI.getWorkbench().getProgressService().busyCursorWhile(
						new IRunnableWithProgress() {
							public void run(IProgressMonitor monitor)
									throws InvocationTargetException,
									InterruptedException {
								buildDiffs(d, styles, monitor, trace);
							}
						});
			} catch (InvocationTargetException e) {
				Activator.handleError(NLS.bind(
						UIText.CommitMessageViewer_errorGettingFileDifference,
						commit.getId()), e.getTargetException(), false);
			} catch (InterruptedException e) {
			}

