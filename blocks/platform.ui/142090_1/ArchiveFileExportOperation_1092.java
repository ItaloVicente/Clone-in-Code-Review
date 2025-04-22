		this(res, filename);
		resourcesToExport = resources;
	}

	protected void addError(String message, Throwable e) {
		errorTable.add(new Status(IStatus.ERROR,
				IDEWorkbenchPlugin.IDE_WORKBENCH, 0, message, e));
	}

	protected int countChildrenOf(IResource checkResource) throws CoreException {
		if (checkResource.getType() == IResource.FILE) {
