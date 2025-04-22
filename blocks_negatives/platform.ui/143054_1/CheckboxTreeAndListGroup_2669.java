    }

    /**
     *	Return a boolean indicating whether all children of the passed tree element
     *	are currently white-checked
     *
     *	@return boolean
     *	@param treeElement java.lang.Object
     */
    protected boolean areAllChildrenWhiteChecked(Object treeElement) {
        Object[] children = treeContentProvider.getChildren(treeElement);
        for (int i = 0; i < children.length; ++i) {
            if (!whiteCheckedTreeItems.contains(children[i])) {
