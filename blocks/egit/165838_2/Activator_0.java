	private void migratePreferences() {
		IEclipsePreferences corePrefs = InstanceScope.INSTANCE
				.getNode(getPluginId());
		boolean changed = false;
		IEclipsePreferences uiPrefs = InstanceScope.INSTANCE
				.getNode("org.eclipse.egit.ui"); //$NON-NLS-1$
		String old_ui_preference_key = "remote_connection_timeout"; //$NON-NLS-1$
		int timeout = corePrefs
				.getInt(GitCorePreferences.core_remoteConnectionTimeout, -1);
		if (timeout < 0) {
			timeout = uiPrefs.getInt(old_ui_preference_key, -1);
			if (timeout > 0) {
				corePrefs.putInt(
						GitCorePreferences.core_remoteConnectionTimeout,
						timeout);
				uiPrefs.remove(old_ui_preference_key);
				changed = true;
			}
		}
		if (changed) {
			try {
				corePrefs.flush();
				uiPrefs.flush();
			} catch (BackingStoreException e) {
				logError(e.getMessage(), e);
			}
		}
	}

