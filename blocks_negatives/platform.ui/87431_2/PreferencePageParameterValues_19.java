				new IRegistryChangeListener() {

					@Override
					public void registryChanged(IRegistryChangeEvent event) {
						if (event.getExtensionDeltas(PlatformUI.PLUGIN_ID,
								IWorkbenchRegistryConstants.PL_PREFERENCES).length > 0) {
							preferenceMap = null;
						}
