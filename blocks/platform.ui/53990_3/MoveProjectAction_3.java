		IRunnableWithProgress op =  monitor -> {
			MoveProjectOperation op1 = new MoveProjectOperation(project, newLocation, IDEWorkbenchMessages.MoveProjectAction_moveTitle);
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
				}
			}
		};
