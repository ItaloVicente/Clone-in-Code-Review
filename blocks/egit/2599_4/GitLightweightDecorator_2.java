		postLabelEvent(elements, false);
	}

	private void postLabelEvent(final Object[] elements,
			final boolean invalidateAllDecorations) {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		boolean updateRoot = false;

		if (elements != null) {
			for (Object element : elements) {
				final IResource resource = getResource(element);
				if (resource != null) {
					if (resource.equals(root)) {
						updateRoot = true;
						break;
					} else {
						try {
							resource.setSessionProperty(REFRESHED_KEY, null);
						} catch (CoreException e) {
						}
					}
				}
			}
		}

		if (invalidateAllDecorations || updateRoot) {
