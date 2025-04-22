
	private static IResource[] getResourcesImpl(GitModelObject[] children) {
		IResource[] result = new IResource[children.length];

		for (int i = 0; i < children.length; i++) {
			IResource resource = null;
			IPath childPath = children[i].getLocation();
			if (children[i].isContainer())
				resource = ROOT.getContainerForLocation(childPath);
			else
				resource = ROOT.getFileForLocation(childPath);

			if (resource != null)
				result[i] = resource;
		}

		return result;

	}

