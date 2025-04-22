		try {
			if (disableRedraw) {
				getControl().setRedraw(false);
			}
			Widget w = internalExpand(elementOrTreePath, true);
			if (w != null) {
				internalExpandToLevel(w, level);
			}
		} finally {
			getControl().setRedraw(true);
