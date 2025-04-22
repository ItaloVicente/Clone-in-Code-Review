		monitor.subTask(provider.getFullPath(folderObject));
		IWorkspace workspace = destinationContainer.getWorkspace();
		IPath containerPath = containerResource.getFullPath();
		IPath resourcePath = containerPath.append(provider
				.getLabel(folderObject));

		if (resourcePath.equals(containerPath)) {
