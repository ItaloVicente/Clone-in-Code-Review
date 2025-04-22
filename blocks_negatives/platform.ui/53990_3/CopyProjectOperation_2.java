		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				org.eclipse.ui.ide.undo.CopyProjectOperation op = new org.eclipse.ui.ide.undo.CopyProjectOperation(
						project, projectName, newLocation,
						IDEWorkbenchMessages.CopyProjectOperation_copyProject);
				op.setModelProviderIds(getModelProviderIds());
				try {
					PlatformUI.getWorkbench().getOperationSupport()
							.getOperationHistory().execute(
									op,
									monitor,
									WorkspaceUndoUtil
											.getUIInfoAdapter(parentShell));
				} catch (final ExecutionException e) {
					if (e.getCause() instanceof CoreException) {
						recordError((CoreException)e.getCause());
					} else {
						throw new InvocationTargetException(e);
					}
