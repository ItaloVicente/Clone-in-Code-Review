		if (event.getElement() == null) {
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace()
					.getRoot();
			try {
				root.setSessionProperty(REFRESH_KEY,
						new Long(System.currentTimeMillis()));
			} catch (CoreException e) {
				handleException(root, e);
			}

			fireLabelEvent(event);
		} else {
			Object[] elements = event.getElements();
			for (Object element : elements) {
				final IResource resource = getResource(element);
				if (resource != null)
					try {
						resource.setSessionProperty(REFRESHED_KEY, null);
					} catch (CoreException e) {
					}
			}

			fireLabelEvent(new LabelProviderChangedEvent(this));
		}
	}

	private void fireLabelEvent(final LabelProviderChangedEvent event) {
