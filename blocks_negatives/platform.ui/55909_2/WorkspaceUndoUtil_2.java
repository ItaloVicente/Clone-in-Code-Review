	static ResourceDescription[] copy(IResource[] resources, IPath destination,
			List resourcesAtDestination, IProgressMonitor monitor,
			IAdaptable uiInfo, boolean pathIncludesName, boolean createVirtual,
			boolean createLinks, String relativeToVariable)
			throws CoreException {

		monitor.beginTask("", resources.length); //$NON-NLS-1$
		monitor
				.setTaskName(UndoMessages.AbstractResourcesOperation_CopyingResourcesProgress);
		List overwrittenResources = new ArrayList();
		for (int i = 0; i < resources.length; i++) {
			IResource source = resources[i];
