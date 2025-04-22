
		private void savePrefs() {
			IEclipsePreferences prefs = InstanceScope.INSTANCE
					.getNode(Activator.getPluginId());
			prefs.putBoolean(
					GitCorePreferences.core_saveCredentialsInSecureStore,
					useSecureStore);
			try {
				prefs.flush();
			} catch (BackingStoreException e) {
				Activator.logError(
						CoreText.EGitSshdSessionFactory_savingPreferencesFailed,
						e);
			}
		}
