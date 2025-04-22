		IResource resource = delta.getResource();

		IResourceDelta[] affectedChildren = delta
				.getAffectedChildren(IResourceDelta.CHANGED);
		for (IResourceDelta element : affectedChildren) {
			if ((element.getFlags() & IResourceDelta.TYPE) != 0) {
				((StructuredViewer) viewer).refresh(resource);
				return;
			}
		}

		int changeFlags = delta.getFlags();
		if ((changeFlags & (IResourceDelta.OPEN | IResourceDelta.SYNC)) != 0) {
			((StructuredViewer) viewer).update(resource, null);
		}

		for (IResourceDelta element : affectedChildren) {
			processDelta(element);
		}


		affectedChildren = delta.getAffectedChildren(IResourceDelta.REMOVED);
		if (affectedChildren.length > 0) {
			Object[] affected = new Object[affectedChildren.length];
			for (int i = 0; i < affectedChildren.length; i++) {
