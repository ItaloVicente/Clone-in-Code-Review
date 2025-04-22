	public void dispose() {
		indexChangedListenerHandle.remove();
		refsChangedListenerHandle.remove();
		if (resourceChangeListener != null)
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
	}

