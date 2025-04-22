		info.update(e);
		dragAgent = getDragAgent(info);
		if (dragAgent != null) {
			try {
				dragging = true;
				isModified = (e.stateMask & SWT.MOD1) != 0;
				startDrag();
			} finally {
				dragging = false;
