		@Override
		public void registryChanged(IRegistryChangeEvent event) {
			final IExtensionDelta[] deltas = event.getExtensionDeltas(PlatformUI.PLUGIN_ID,
					IWorkbenchRegistryConstants.PL_STARTUP);
			if (deltas.length == 0) {
				return;
