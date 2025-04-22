	private void setTopControl(CommonViewer viewer) {
		if (!repositories.isEmpty()
				|| RepositoryGroups.getInstance().hasGroups()) {
			layout.topControl = viewer.getControl();
		} else {
			layout.topControl = emptyArea;
		}
	}

	private void afterRefresh(CommonViewer viewer) {
		Control currentTop = layout.topControl;
		setTopControl(viewer);
		if (currentTop != layout.topControl) {
			emptyArea.getParent().layout(true, true);
		}
	}

