	public IWorkingSetPage createWorkingSetPage() {
		Object page = null;

		if (pageClassName != null) {
			try {
				page = WorkbenchPlugin.createExtension(configElement, ATT_PAGE_CLASS);
			} catch (CoreException exception) {
				WorkbenchPlugin.log("Unable to create working set page: " + //$NON-NLS-1$
						pageClassName, exception.getStatus());
			}
		}
		return (IWorkingSetPage) page;
	}

	public ImageDescriptor getIcon() {
		if (icon == null) {
