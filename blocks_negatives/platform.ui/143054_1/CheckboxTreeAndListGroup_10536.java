        }

        return result.iterator();
    }

    /**
     *	Answer a collection of all of the checked elements in the tree portion
     *	of self
     *
     *	@return java.util.Vector
     */
    public Set getAllCheckedTreeItems() {
        return checkedStateStore.keySet();
    }

    /**
     *	Answer the number of elements that have been checked by the
     *	user.
     *
     *	@return int
     */
    public int getCheckedElementCount() {
        return checkedStateStore.size();
    }

    /**
     *	Return a count of the number of list items associated with a
     *	given tree item.
     *
     *	@return int
     *	@param treeElement java.lang.Object
     */
    protected int getListItemsSize(Object treeElement) {
        Object[] elements = listContentProvider.getElements(treeElement);
        return elements.length;
    }

    /**
     * Get the table the list viewer uses.
     * @return org.eclipse.swt.widgets.Table
     */
    public Table getListTable() {
        return this.listViewer.getTable();
    }

    /**
     *	Logically gray-check all ancestors of treeItem by ensuring that they
     *	appear in the checked table
     */
    protected void grayCheckHierarchy(Object treeElement) {

        if (checkedStateStore.containsKey(treeElement)) {
