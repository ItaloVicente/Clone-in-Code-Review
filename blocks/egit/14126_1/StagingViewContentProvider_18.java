	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof StagingEntry)
			return null;
		int presentation = stagingView.getPresentation();
		if (parentElement instanceof StagingFolderEntry) {
			if (presentation == StagingView.PRESENTATION_COMPRESSED_FOLDERS)
				return getChildResources((StagingFolderEntry) parentElement);
			else
				return getFolderChildren((StagingFolderEntry) parentElement);
		} else {
			if (presentation == StagingView.PRESENTATION_FLAT)
				return content;
			else if (presentation == StagingView.PRESENTATION_COMPRESSED_FOLDERS)
				return getCompressedRoots();
			else
				return getRoots();
		}
