	public static IWorkbench getWorkbench() {
		if (Workbench.getInstance() == null) {
			throw new IllegalStateException(WorkbenchMessages.PlatformUI_NoWorkbench);
		}
		return Workbench.getInstance();
	}
