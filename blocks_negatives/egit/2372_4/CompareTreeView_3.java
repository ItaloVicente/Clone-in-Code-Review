		IWorkbenchAction deletedOnlyAction = new BooleanPrefAction(store,
				UIPreferences.TREE_COMPARE_DELETED_ONLY,
				UIText.CompareTreeView_DeletedOnlyTooltip) {
			@Override
			void apply(boolean value) {
				buildTrees();
			}
		};
		deletedOnlyAction.setImageDescriptor(UIIcons.ELCL16_DELETE);
		deletedOnlyAction.setEnabled(false);
		actionsToDispose.add(deletedOnlyAction);
		getViewSite().getActionBars().getToolBarManager()
				.add(deletedOnlyAction);

