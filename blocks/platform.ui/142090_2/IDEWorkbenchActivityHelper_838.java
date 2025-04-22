					return pluginId;
				}
			});
		}
	}

	private IResourceChangeListener getChangeListener() {
		return event -> {
			if (!WorkbenchActivityHelper.isFiltering()) {
