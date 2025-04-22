
			replaceElementInSelection(getPathParent(parent), oldElement, newElement, selection);
		}
	}

	private Object getPathParent(Object parent) {
		return parent == viewer.getInput() ? null : parent;
	}

	private void replaceElementInSelection(final Object parent, final Object oldElement, final Object newElement,
			final ITreeSelection selection) {
		IElementComparer comparer = viewer.getComparer();
		TreePath[] paths = selection.getPaths();
		for (int i = 0; i < paths.length; i++) {
			TreePath path = paths[i];
			Object[] segments = new Object[path.getSegmentCount()];
			boolean replacePath = false;
			for (int j = 0; j < path.getSegmentCount(); j++) {
				segments[j] = path.getSegment(j);
				Object pathParent = j > 0 ? path.getSegment(j - 1) : null;
				Object pathElement = path.getSegment(j);
				if (!replacePath && eq(comparer, parent, pathParent) && eq(comparer, oldElement, pathElement)) {
					segments[j] = newElement;
					replacePath = true;
				}
			}
			if (replacePath) {
				paths[i] = new TreePath(segments);
			}
