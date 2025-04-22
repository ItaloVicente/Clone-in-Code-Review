	@Override
	public void run() {
		Viewer viewer = getBrowser().getViewer();
		if (viewer instanceof AbstractTreeViewer)
			((AbstractTreeViewer) viewer).expandAll();
	}
