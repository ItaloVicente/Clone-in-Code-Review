		return result;
	}

	protected void createLeadupDirectoriesFor(IResource childResource) {
		IPath resourcePath = childResource.getFullPath().removeLastSegments(1);

		for (int i = 0; i < resourcePath.segmentCount(); i++) {
			path = path.append(resourcePath.segment(i));
			exporter.createFolder(path);
		}
	}

	protected void exportAllResources() throws InterruptedException {
		if (resource.getType() == IResource.FILE) {
