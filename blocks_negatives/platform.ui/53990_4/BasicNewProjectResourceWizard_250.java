		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				CreateProjectOperation op = new CreateProjectOperation(
						description, ResourceMessages.NewProject_windowTitle);
				try {
					op.execute(monitor, WorkspaceUndoUtil
						.getUIInfoAdapter(getShell()));
				} catch (ExecutionException e) {
					throw new InvocationTargetException(e);
				}
			}
		};
