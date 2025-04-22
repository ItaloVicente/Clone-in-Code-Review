	private final IPreferenceChangeListener prefListener = event -> {
		if (!RepositoryUtil.PREFS_DIRECTORIES_REL.equals(event.getKey())) {
			return;
		}
		Repository repo = getRepository();
		if (repo == null
				|| !Activator.getDefault().getRepositoryUtil().contains(repo)) {
			Control control = refLogTreeViewer.getControl();
			if (!control.isDisposed()) {
				control.getDisplay().asyncExec(() -> {
					if (!control.isDisposed()) {
						updateView(null);
					}
				});
			}
		}
	};

