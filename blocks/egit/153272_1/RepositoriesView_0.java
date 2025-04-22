	private void afterRefresh(CommonViewer viewer) {
		Control currentTop = layout.topControl;
		if (!repositories.isEmpty()
				|| RepositoryGroups.getInstance().hasGroups()) {
			layout.topControl = viewer.getControl();
		} else {
			layout.topControl = emptyArea;
		}
		if (currentTop != layout.topControl) {
			emptyArea.getParent().layout(true, true);
		}
	}

