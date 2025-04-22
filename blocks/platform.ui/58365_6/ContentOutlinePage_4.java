    public void dispose() {
        if (toggleTreeFilterAction != null) {
            ViewsPlugin.getDefault().getDialogSettings().put(TOGGLE_FILTER_TREE_ACTION_IS_CHECKED,
            toggleTreeFilterAction.isChecked());
        }
        super.dispose();
