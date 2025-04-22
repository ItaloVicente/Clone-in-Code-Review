		if (repository == null || mayBeCommitted.isEmpty()) {
			return preselectionCandidates;
		}
		List<String> selectedDirectories = new ArrayList<>();
		Set<String> selectedFiles = new HashSet<>();
		for (IResource rsc : resourcesSelected) {
			IPath p = ResourceUtil.getRepositoryRelativePath(rsc.getLocation(),
					repository);
			if (p != null) {
				if (p.isEmpty()) {
					return mayBeCommitted;
				} else {
					String rscPath = p.toString();
					if (rsc.getType() == IResource.FILE) {
						selectedFiles.add(rscPath);
					} else {
						selectedDirectories.add(rscPath + '/');
