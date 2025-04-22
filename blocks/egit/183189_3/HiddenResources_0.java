
	private void linkFile(IFile file, URI uri, IProgressMonitor monitor)
			throws CoreException {
		synchronized (lock) {
			boolean linkingDisabled = Platform.getPreferencesService()
					.getBoolean(ResourcesPlugin.PI_RESOURCES,
							ResourcesPlugin.PREF_DISABLE_LINKING, false, null);
			IEclipsePreferences prefs = null;
			IPreferenceChangeListener listener = null;
			AtomicBoolean prefChanged = new AtomicBoolean();
			if (linkingDisabled) {
				prefs = InstanceScope.INSTANCE
						.getNode(ResourcesPlugin.PI_RESOURCES);
				prefs.putBoolean(ResourcesPlugin.PREF_DISABLE_LINKING, false);
				listener = event -> {
					if (ResourcesPlugin.PREF_DISABLE_LINKING
							.equals(event.getKey())) {
						prefChanged.set(true);
					}
				};
				prefs.addPreferenceChangeListener(listener);
			}
			try {
				file.createLink(uri, IResource.NONE, monitor);
			} finally {
				if (prefs != null) {
					prefs.removePreferenceChangeListener(listener);
					if (!prefChanged.get()) {
						prefs.putBoolean(ResourcesPlugin.PREF_DISABLE_LINKING,
								linkingDisabled);
					}
				}
			}
		}
	}
