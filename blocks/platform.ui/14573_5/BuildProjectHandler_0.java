	private Map buildActions = Collections.synchronizedMap(new HashMap());
	
	public BuildProjectHandler() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			workbench.addWindowListener(this);
		}
	}
	
	protected void finalize() throws Throwable {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			workbench.removeWindowListener(this);
		}
		super.finalize();
	}
	
