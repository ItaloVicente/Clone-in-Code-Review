	private static void addPath(Object element, List<IPath> paths) {
		if (element instanceof StagingEntry) {
			StagingEntry stagingEntry = (StagingEntry) element;
			paths.add(stagingEntry.getLocation());
		} else if (element instanceof StagingFolderEntry) {
			for (Object child : ((StagingFolderEntry) element).getChildren()) {
				addPath(child, paths);
			}
		}
	}

