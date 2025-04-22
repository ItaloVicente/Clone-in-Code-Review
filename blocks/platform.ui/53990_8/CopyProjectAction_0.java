		IRunnableWithProgress op = monitor -> {
			org.eclipse.ui.ide.undo.CopyProjectOperation op1 = new org.eclipse.ui.ide.undo.CopyProjectOperation(
					project, projectName, newLocation, getText());
			op1.setModelProviderIds(getModelProviderIds());
			try {
				PlatformUI.getWorkbench().getOperationSupport()
						.getOperationHistory().execute(op1, monitor,
								WorkspaceUndoUtil.getUIInfoAdapter(shellProvider.getShell()));
			} catch (ExecutionException e) {
				if (e.getCause() instanceof CoreException) {
					recordError((CoreException)e.getCause());
				} else {
					IDEWorkbenchPlugin.log(e.getMessage(), e);
					displayError(e.getMessage());
