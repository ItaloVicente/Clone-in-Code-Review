		final ArrayList<String> paths;
		if (inResources != null) {
			paths = new ArrayList<String>(inResources.length);
			for (final IResource r : inResources) {
				final RepositoryMapping map = RepositoryMapping.getMapping(r);
				if (map == null)
					continue;
				if (db != map.getRepository()) {
					setErrorMessage(UIText.AbstractHistoryCommanndHandler_NoUniqueRepository);
					return false;
				}
