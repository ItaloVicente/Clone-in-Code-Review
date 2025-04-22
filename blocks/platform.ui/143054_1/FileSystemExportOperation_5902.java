		}

		return count;
	}

	protected int countSelectedResources() throws CoreException {
		int result = 0;
		Iterator resources = resourcesToExport.iterator();

		while (resources.hasNext()) {
