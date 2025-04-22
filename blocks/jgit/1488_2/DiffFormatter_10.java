	public boolean isDetectRenames() {
		return renameDetector != null;
	}

	public void setDetectRenames(boolean on) {
		if (on && renameDetector == null) {
			assertHaveRepository();
			renameDetector = new RenameDetector(db);
		} else if (!on)
			renameDetector = null;
	}

	public RenameDetector getRenameDetector() {
		return renameDetector;
	}

	public void setProgressMonitor(ProgressMonitor pm) {
		progressMonitor = pm;
	}

	public void setPathFilter(TreeFilter filter) {
		pathFilter = filter != null ? filter : TreeFilter.ALL;
	}

	public TreeFilter getPathFilter() {
		return pathFilter;
	}

