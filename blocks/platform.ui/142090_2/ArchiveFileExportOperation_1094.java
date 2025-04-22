		return result;
	}

	protected void exportResource(IResource exportResource)
			throws InterruptedException {
		exportResource(exportResource, 1);
	}

	private String createDestinationName(int leadupDepth, IResource exportResource) {
		IPath fullPath = exportResource.getFullPath();
		if (createLeadupStructure) {
			return fullPath.makeRelative().toString();
		}
