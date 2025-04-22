			if (!repositories.isEmpty()) {
				layout.topControl = getCommonViewer().getControl();
			} else {
				layout.topControl = emptyArea;
			}
			emptyArea.getParent().layout(true, true);
