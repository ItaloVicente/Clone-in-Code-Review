	private Map buildActions = Collections.synchronizedMap(new HashMap());
	
	public BuildProjectHandler() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			workbench.addWindowListener(this);
		}
	}
	
	public void dispose() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			workbench.removeWindowListener(this);
		}
		super.dispose();
	}
	
