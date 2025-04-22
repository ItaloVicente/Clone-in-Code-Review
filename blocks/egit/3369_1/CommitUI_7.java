		for (String fileName : files) {
			URI uri = new File(repo.getWorkTree(), fileName).toURI();
			IFile[] workspaceFiles = root.findFilesForLocationURI(uri);
			if (workspaceFiles.length > 0) {
				IFile file = workspaceFiles[0];
				for (IResource resource : selectedResources) {
					if (resource.contains(file)) {
						preselectionCandidates.add(fileName);
						break;
					}
