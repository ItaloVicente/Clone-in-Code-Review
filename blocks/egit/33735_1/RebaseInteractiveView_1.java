
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
