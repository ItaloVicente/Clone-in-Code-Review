
		final List<StyleRange> styles = new ArrayList<StyleRange>();
		final StringBuilder d = new StringBuilder();
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(
					new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							format(styles, d, monitor);
						}
					});

		} catch (InvocationTargetException e) {
			Activator.handleError(NLS.bind(
					UIText.CommitMessageViewer_errorGettingFileDifference,
					commit.getId()), e.getTargetException(), false);
		} catch (InterruptedException e) {
