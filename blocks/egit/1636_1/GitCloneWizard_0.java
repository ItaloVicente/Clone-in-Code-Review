	public void runCloneOperation(IWizardContainer container) {
		try {
				container.run(true, true,
						new IRunnableWithProgress() {
							public void run(IProgressMonitor monitor)
									throws InvocationTargetException,
									InterruptedException {
								executeCloneOperation(cloneOperation, monitor);
							}
						});
			} catch (InvocationTargetException e) {
				Activator.handleError(UIText.GitCloneWizard_failed,
						e.getCause(), true);
			} catch (InterruptedException e) {
