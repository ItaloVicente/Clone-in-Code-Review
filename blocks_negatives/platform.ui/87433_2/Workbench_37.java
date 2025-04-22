			final String disabledPlugins = PrefUtil.getInternalPreferenceStore().getString(
					IPreferenceConstants.PLUGINS_NOT_ACTIVATED_ON_STARTUP);

			for (IExtensionDelta delta : deltas) {
				IExtension extension = delta.getExtension();
				if (delta.getKind() == IExtensionDelta.REMOVED) {
					continue;
				}
