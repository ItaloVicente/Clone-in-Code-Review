	@Override
	public void run() {
		Viewer viewer = getBrowser().getViewer();
		if (viewer instanceof StructuredViewer)
			((StructuredViewer) viewer).addFilter(new Filter());
	}
