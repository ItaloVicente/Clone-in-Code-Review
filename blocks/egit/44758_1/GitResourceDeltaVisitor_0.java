
	private Collection<String> getFilesToUpdateLazily() {
		if (filesToUpdate == null) {
			filesToUpdate = new HashSet<String>();
		}

		return filesToUpdate;
	}

	private Collection<IResource> getResourcesToUpdateLazily() {
		if (resourcesToUpdate == null) {
			resourcesToUpdate = new HashSet<IResource>();
		}
		return resourcesToUpdate;
	}
