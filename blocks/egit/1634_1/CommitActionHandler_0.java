			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					try {
						buildIndexHeadDiffList(projects, monitor);
					} catch (IOException e) {
						throw new InvocationTargetException(e);
					}
				}
			});
		} catch (InvocationTargetException e) {
			Activator.handleError(UIText.CommitAction_errorComputingDiffs, e.getCause(),
