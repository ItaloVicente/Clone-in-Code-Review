	private void add(MTrimBar trimBar, int idx, MToolBar toolBar) {
		if (idx < 0) {
			idx = trimBar.getChildren().size() - 1;
			while (idx > -1) {
				if ("afterAdditions".equals(trimBar.getChildren().get(idx).getElementId())) { //$NON-NLS-1$ 	
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
	}

