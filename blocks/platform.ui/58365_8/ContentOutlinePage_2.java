        toggleTreeFilterAction = new ToggleTreeFilterAction(filteredTreeViewer);
        toggleTreeFilterAction.setChecked(
				WorkbenchPlugin.getDefault().getDialogSettings().getBoolean(TOGGLE_FILTER_TREE_ACTION_IS_CHECKED));
        IContributionManager toolBarManager = pageSite.getActionBars().getToolBarManager();
        toolBarManager.add(toggleTreeFilterAction);
        toolBarManager.update(true);
