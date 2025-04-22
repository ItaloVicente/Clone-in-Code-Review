		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null)
		ISelectionService srv = (ISelectionService) activeWorkbenchWindow.getService(ISelectionService.class);
		if (srv.getSelection() instanceof StructuredSelection) {
			return ((StructuredSelection) srv.getSelection()).size() == 1;
		}
		return false;
