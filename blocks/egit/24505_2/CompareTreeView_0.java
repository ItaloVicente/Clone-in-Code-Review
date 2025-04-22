		compareModeAction = new BooleanPrefAction(
				(IPersistentPreferenceStore) Activator.getDefault()
						.getPreferenceStore(),
				UIPreferences.TREE_COMPARE_COMPARE_MODE,
				UIText.CompareTreeView_CompareModeTooltip) {
			@Override
			public void apply(boolean value) {
			}
		};
		compareModeAction.setImageDescriptor(UIIcons.ELCL16_COMPARE_VIEW);
		compareModeAction.setEnabled(true);
		actionsToDispose.add(compareModeAction);
		getViewSite().getActionBars().getToolBarManager()
				.add(compareModeAction);

