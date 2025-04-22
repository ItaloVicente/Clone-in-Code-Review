	static ResourceDescription[] copy(IResource[] resources, IPath destination, List<IResource> resourcesAtDestination,
			IProgressMonitor monitor, IAdaptable uiInfo, boolean pathIncludesName, boolean createVirtual,
			boolean createLinks, String relativeToVariable) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, resources.length);
		subMonitor.setTaskName(UndoMessages.AbstractResourcesOperation_CopyingResourcesProgress);
		List<ResourceDescription> overwrittenResources = new ArrayList<>();
		for (IResource source : resources) {
			SubMonitor iterationProgress = subMonitor.newChild(1).setWorkRemaining(100);
