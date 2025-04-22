	ShowInContext getShowInContext() {
		if (diffViewer != null && diffViewer.getControl().isFocusControl())
			return diffViewer.getShowInContext();
		else
			return null;
	}

