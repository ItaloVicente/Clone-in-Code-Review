		prefListener = new IPreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent event) {
				if (!RepositoryUtil.PREFS_DIRECTORIES.equals(event.getKey()))
					return;

				final Repository repo = currentRepository;
				if (repo == null)
					return;

				if (Activator.getDefault().getRepositoryUtil().contains(repo))
					return;

				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						currentRepository = null;
						showRepository(null);
					}
				});
			}
		};

		InstanceScope.INSTANCE.getNode(
				org.eclipse.egit.core.Activator.getPluginId())
				.addPreferenceChangeListener(prefListener);

