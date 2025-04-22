        filteredTreeViewer = new FilteredTree(parent, getTreeStyle(), new PatternFilter(true));
        filteredTreeViewer.getViewer().addSelectionChangedListener(this);

        if (toggleTreeFilterAction != null) {
			filteredTreeViewer.setShowFilterControls(
					WorkbenchPlugin.getDefault().getDialogSettings().getBoolean(TOGGLE_FILTER_TREE_ACTION_IS_CHECKED));
            toggleTreeFilterAction.setFilteredTree(filteredTreeViewer);
        }
