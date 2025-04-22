		return text;
	}

	String getFrameToolTipText(Object element) {
		if (element instanceof IResource) {
			IPath path = ((IResource) element).getFullPath();
			if (path.isRoot()) {
