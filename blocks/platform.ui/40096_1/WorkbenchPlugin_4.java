
	public IEclipseContext getWorkbenchContext() {
		if (workbenchContext == null) {
			ServiceReference<org.eclipse.e4.ui.workbench.IWorkbench> ref = getBundleContext().getServiceReference(
					org.eclipse.e4.ui.workbench.IWorkbench.class);
			if (ref != null) {
				workbenchContext = getBundleContext().getService(ref).getApplication().getContext();
			}
		}
		return workbenchContext;
	}
