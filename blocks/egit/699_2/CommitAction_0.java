	private Collection<IFile> getSelectedFiles() {
		List<IFile> preselectionCandidates = new ArrayList<IFile>();
		IResource[] selectedResources = getSelectedResources();
		for (IFile file : files) {
			for (IResource resource : selectedResources) {
				if (resource.contains(file)) {
					preselectionCandidates.add(file);
					break;
				}
			}
		}
		return preselectionCandidates;
	}

