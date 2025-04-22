        Object[] children = ((ITreeContentProvider) ((AbstractTreeViewer) viewer)
                .getContentProvider()).getChildren(element);
        if (children.length > 0) {
			return filter(viewer, element, children).length > 0;
		}

