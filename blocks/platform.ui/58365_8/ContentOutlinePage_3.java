        if (filteredTreeViewer != null || filteredTreeViewer.isDisposed()) {
            filteredTreeViewer.getViewer().setSelection(selection);
        }
    }

    @Override
    public void dispose() {
        if (toggleTreeFilterAction != null) {
			WorkbenchPlugin.getDefault().getDialogSettings().put(TOGGLE_FILTER_TREE_ACTION_IS_CHECKED,
            toggleTreeFilterAction.isChecked());
