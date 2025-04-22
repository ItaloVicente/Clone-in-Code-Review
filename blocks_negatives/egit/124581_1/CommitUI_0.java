		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (String fileName : mayBeCommitted) {
			URI uri = new File(repository.getWorkTree(), fileName).toURI();
			IFile[] workspaceFiles = root.findFilesForLocationURI(uri);
			if (workspaceFiles.length > 0) {
				IFile file = workspaceFiles[0];
				for (IResource resource : resourcesSelected) {
					if (resource.contains(file)) {
						preselectionCandidates.add(fileName);
						break;
