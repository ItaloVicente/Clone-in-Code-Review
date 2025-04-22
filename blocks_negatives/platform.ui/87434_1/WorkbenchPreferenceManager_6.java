				new IRegistryChangeListener() {

					@Override
					public void registryChanged(IRegistryChangeEvent event) {
						if (event.getExtensionDeltas(PlatformUI.PLUGIN_ID,
								IWorkbenchRegistryConstants.PL_KEYWORDS).length > 0) {
							for (Iterator j = getElements(
									PreferenceManager.POST_ORDER).iterator(); j
									.hasNext();) {
								((WorkbenchPreferenceNode) j.next())
										.clearKeywords();
							}
