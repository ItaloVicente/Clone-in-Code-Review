		IWorkbenchAction addedOnlyAction = new BooleanPrefAction(store,
				UIPreferences.TREE_COMPARE_ADDED_ONLY,
				UIText.CompareTreeView_AddedOnlyTooltip) {
			@Override
			void apply(boolean value) {
				buildTrees();
			}
		};
		addedOnlyAction.setImageDescriptor(UIIcons.ELCL16_ADD);
		addedOnlyAction.setEnabled(false);
		actionsToDispose.add(addedOnlyAction);
		getViewSite().getActionBars().getToolBarManager().add(addedOnlyAction);

		showEqualsAction = new BooleanPrefAction(store,
