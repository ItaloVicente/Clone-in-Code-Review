	private void add(MTrimBar trimBar, int idx, MToolBar toolBar) {
		if (trimBar == topTrim && idx < 0) {
			idx = trimBar.getChildren().size() - 1;
			while (idx >= 0) {
				if (IWorkbenchConstants.TRIM_PERSPECTIVE_SPACER.equals(trimBar.getChildren()
						.get(idx).getElementId())) {
					break;
				}
				idx--;
			}
		}
		if (idx < 0) {
			trimBar.getChildren().add(toolBar);
		} else {
			trimBar.getChildren().add(idx, toolBar);
		}
