		changedResources = null;
	}

	private Set<String> getUntrackedFolders(IndexDiff indexDiff) {
		HashSet<String> result = new HashSet<String>();
		for (String folder:indexDiff.getUntrackedFolders())
			result.add(folder + "/"); //$NON-NLS-1$
		return result;
