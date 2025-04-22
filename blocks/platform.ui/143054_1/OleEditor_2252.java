		if (resource != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					resourceListener);
			resource = null;
		}

		if (oleTitleImage != null) {
			oleTitleImage.dispose();
			oleTitleImage = null;
		}
	}

	public void doPrint() {
		if (clientSite == null)
			return;
