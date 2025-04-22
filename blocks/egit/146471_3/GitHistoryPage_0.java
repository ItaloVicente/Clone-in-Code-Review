	private final IPreferenceChangeListener prefListener = event -> {
		if (!RepositoryUtil.PREFS_DIRECTORIES_REL.equals(event.getKey())) {
			return;
		}
		if (currentRepo == null || !Activator.getDefault().getRepositoryUtil()
				.contains(currentRepo)) {
			Control control = historyControl;
			if (!control.isDisposed()) {
				control.getDisplay().asyncExec(() -> {
					if (!control.isDisposed()) {
						setInput(null);
					}
				});
			}
		}
	};

