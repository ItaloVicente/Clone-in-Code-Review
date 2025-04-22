		String type = element.getName();
		if (type.equals(IWorkbenchRegistryConstants.TAG_PERSPECTIVE_EXTENSION)) {
			String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_TARGET_ID);
			if (targetID.equals(id) || "*".equals(id)) { //$NON-NLS-1$
				if (tracker != null) {
					tracker.registerObject(element.getDeclaringExtension(), new DirtyPerspectiveMarker(id),
							IExtensionTracker.REF_STRONG);
