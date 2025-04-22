    }

    /**
     *  Callback that's invoked when the checked status of an item in the tree
     *  is changed by the user.
     */
    private void treeItemChecked(Object treeElement, boolean state) {
        setTreeChecked(treeElement, state);
        Object parent = treeContentProvider.getParent(treeElement);
        if (parent == null || parent instanceof IWorkspaceRoot) {
