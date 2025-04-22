	private void runInParentContainer(final CloneOperation op) {
		Runnable runInParentContainer = new Runnable() {
			public void run() {
				try {
					parentContainer.run(true, true,
							new IRunnableWithProgress() {
								public void run(IProgressMonitor monitor)
										throws InvocationTargetException,
										InterruptedException {
									executeCloneOperation(op, monitor);
								}
							});
				} catch (InvocationTargetException e) {
					Activator.handleError(e.getMessage(), e, true);
				} catch (InterruptedException e) {
