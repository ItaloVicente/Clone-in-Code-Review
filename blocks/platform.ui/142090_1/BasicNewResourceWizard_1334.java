		setDefaultPageImageDescriptor(desc);
	}

	protected void selectAndReveal(IResource newResource) {
		selectAndReveal(newResource, getWorkbench().getActiveWorkbenchWindow());
	}

	public static void selectAndReveal(IResource resource,
			IWorkbenchWindow window) {
		if (window == null || resource == null) {
