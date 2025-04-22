		Control control = getControl();
		try {
			control.setRedraw(false);
			Widget w = internalGetWidgetToSelect(elementOrTreePath);
			if (w != null) {
				internalCollapseToLevel(w, level);
			}
		} finally {
			control.setRedraw(true);
