
			replaceElementInSelection(oldElement, newElement, selection);
		}
	}

	private void replaceElementInSelection(final Object oldElement, final Object newElement,
			final ITreeSelection selection) {
		TreePath[] paths = selection.getPaths();
		for (int i = 0; i < paths.length; i++) {
			if (eq(viewer.getComparer(), paths[i].getLastSegment(), oldElement)) {
				paths[i] = paths[i].getParentPath().createChildPath(newElement);
			}
