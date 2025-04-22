    public void createControl(Composite parent) {
        filteredTreeViewer = new FilteredTree(parent, getTreeStyle(), new PatternFilter(true));
        filteredTreeViewer.setShowFilterControls(
                ViewsPlugin.getDefault().getDialogSettings().getBoolean(TOGGLE_FILTER_TREE_ACTION_IS_CHECKED));
        filteredTreeViewer.getViewer().addSelectionChangedListener(this);
        if (toggleTreeFilterAction != null) {
            toggleTreeFilterAction.setFilteredTree(filteredTreeViewer);
        }
