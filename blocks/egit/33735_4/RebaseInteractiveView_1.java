
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

		IActionBars actionBars = getViewSite().getActionBars();
		IToolBarManager toolbar = actionBars.getToolBarManager();

		listenOnRepositoryViewSelection = RebaseInteractivePreferences
				.isReactOnSelection();

		Action linkSelectionAction = new BooleanPrefAction(
				(IPersistentPreferenceStore) Activator.getDefault()
						.getPreferenceStore(),
				UIPreferences.REBASE_INTERACTIVE_SYNC_SELECTION,
				UIText.InteractiveRebaseView_LinkSelection) {
			@Override
			public void apply(boolean value) {
				listenOnRepositoryViewSelection = value;
			}
		};
		linkSelectionAction.setImageDescriptor(UIIcons.ELCL16_SYNCED);
		toolbar.add(linkSelectionAction);
