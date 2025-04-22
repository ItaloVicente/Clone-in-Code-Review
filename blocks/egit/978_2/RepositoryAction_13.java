	protected IStructuredSelection getSelection() {
		ISelectionService srv = (ISelectionService) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getService(ISelectionService.class);
		if (srv == null)
			return new StructuredSelection();
		return (IStructuredSelection) srv.getSelection();
