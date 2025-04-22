
	public ShowInContext getShowInContext() {
		if (fileViewer != null && fileViewer.getControl().isFocusControl())
			return fileViewer.getShowInContext();
		else
			return null;
	}
