		Action refreshAction = new Action(UIText.StagingView_Refresh, IAction.AS_PUSH_BUTTON) {
			public void run() {
				reload(currentRepository);
			}
		};
		refreshAction.setImageDescriptor(UIIcons.ELCL16_REFRESH);
		toolbar.add(refreshAction);

		Action linkSelectionAction = new BooleanPrefAction(
				(IPersistentPreferenceStore) Activator.getDefault()
						.getPreferenceStore(),
				UIPreferences.STAGING_VIEW_SYNC_SELECTION,
				UIText.StagingView_LinkSelection) {
			@Override
			public void apply(boolean value) {
				reactOnSelection = value;
			}
		};
		linkSelectionAction.setImageDescriptor(UIIcons.ELCL16_SYNCED);
		toolbar.add(linkSelectionAction);

		toolbar.add(new Separator());

