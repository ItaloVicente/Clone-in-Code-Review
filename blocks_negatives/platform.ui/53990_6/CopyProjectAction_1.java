		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
				org.eclipse.ui.ide.undo.CopyProjectOperation op = new org.eclipse.ui.ide.undo.CopyProjectOperation(
						project, projectName, newLocation, getText());
				op.setModelProviderIds(getModelProviderIds());
				try {
					PlatformUI.getWorkbench().getOperationSupport()
							.getOperationHistory().execute(op, monitor,
									WorkspaceUndoUtil.getUIInfoAdapter(shellProvider.getShell()));
				} catch (ExecutionException e) {
					if (e.getCause() instanceof CoreException) {
						recordError((CoreException)e.getCause());
					} else {
						IDEWorkbenchPlugin.log(e.getMessage(), e);
						displayError(e.getMessage());
					}
