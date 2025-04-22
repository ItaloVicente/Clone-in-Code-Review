	private @Nullable IWorkbenchPage getActivePage() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		return window == null ? null : window.getActivePage();
	}

