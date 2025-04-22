	@Nullable
	private static Shell getActiveWindowShell() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		return window == null ? null : window.getShell();
	}

