        tree.setLayoutData(data);
        tree.setFont(parent.getFont());

        treeViewer = new CheckboxTreeViewer(tree);
        treeViewer.setUseHashlookup(true);
        treeViewer.setContentProvider(treeContentProvider);
        treeViewer.setLabelProvider(treeLabelProvider);
        treeViewer.addTreeListener(treeListener);
        treeViewer.addCheckStateListener(checkListener);
        treeViewer.addSelectionChangedListener(selectionListener);
    }

    /**
     * Returns a boolean indicating whether the passed tree element should be
     * at LEAST gray-checked.  Note that this method does not consider whether
     * it should be white-checked, so a specified tree item which should be
     * white-checked will result in a <code>true</code> answer from this method.
     * To determine whether a tree item should be white-checked use method
     * #determineShouldBeWhiteChecked(Object).
     *
     * @param treeElement java.lang.Object
     * @return boolean
     * @see #determineShouldBeWhiteChecked(Object)
     */
    private boolean determineShouldBeAtLeastGrayChecked(Object treeElement) {
        List checked = (List) checkedStateStore.get(treeElement);
        if (checked != null && (!checked.isEmpty())) {
