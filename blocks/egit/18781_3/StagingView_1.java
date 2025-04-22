	private void calculateNodesToExpand(Set<IPath> paths, Object[] elements,
			List<StagingFolderEntry> result) {
		if (elements == null)
			return;

		for (Object element : elements) {
			if (element instanceof StagingFolderEntry) {
				StagingFolderEntry folder = (StagingFolderEntry) element;
				if (paths.contains(folder.getPath())) {
					result.add(folder);
					calculateNodesToExpand(paths, folder.getChildren(), result);
				}
			}
		}
	}

