			if (viewer instanceof AbstractTreeViewer) {
				((AbstractTreeViewer) viewer).remove(affected);
			} else {
				((StructuredViewer) viewer).refresh(resource);
			}
		}

		affectedChildren = delta.getAffectedChildren(IResourceDelta.ADDED);
		if (affectedChildren.length > 0) {
			Object[] affected = new Object[affectedChildren.length];
			for (int i = 0; i < affectedChildren.length; i++) {
