	public RepositoryActionHandler() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow != null) {
			activeWorkbenchWindow.getSelectionService()
					.addPostSelectionListener(new ISelectionListener() {
						public void selectionChanged(IWorkbenchPart part,
								ISelection selection) {
							fireHandlerChanged(new HandlerEvent(
									RepositoryActionHandler.this, true, false));
						}
					});
		}
	}

