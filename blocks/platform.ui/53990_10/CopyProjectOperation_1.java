		IRunnableWithProgress op = monitor -> {
			org.eclipse.ui.ide.undo.CopyProjectOperation op1 = new org.eclipse.ui.ide.undo.CopyProjectOperation(
					project, projectName, newLocation,
					IDEWorkbenchMessages.CopyProjectOperation_copyProject);
			op1.setModelProviderIds(getModelProviderIds());
			try {
				PlatformUI.getWorkbench().getOperationSupport()
						.getOperationHistory().execute(
								op1,
								monitor,
								WorkspaceUndoUtil
										.getUIInfoAdapter(parentShell));
			} catch (final ExecutionException e) {
				if (e.getCause() instanceof CoreException) {
					recordError((CoreException)e.getCause());
				} else {
					throw new InvocationTargetException(e);
