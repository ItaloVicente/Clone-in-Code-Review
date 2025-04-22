		Platform.getExtensionRegistry().addRegistryChangeListener(event -> {
			if (event.getExtensionDeltas(PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_KEYWORDS).length > 0) {
				for (Object element : getElements(PreferenceManager.POST_ORDER)) {
					((WorkbenchPreferenceNode) element).clearKeywords();
				}
			}
		});
