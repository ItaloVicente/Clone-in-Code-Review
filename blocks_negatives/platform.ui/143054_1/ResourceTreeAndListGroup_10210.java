        return parentName.append(elementText).toString();
    }

    /**
     * Return a count of the number of list items associated with a
     * given tree item.
     *
     * @param treeElement
     * @return the list item count
     */
    private int getListItemsSize(Object treeElement) {
        Object[] elements = listContentProvider.getElements(treeElement);
        return elements.length;
    }


    /**
     *	Logically gray-check all ancestors of treeItem by ensuring that they
     *	appear in the checked table
     */
    private void grayCheckHierarchy(Object treeElement) {
        expandTreeElement(treeElement);
        if (checkedStateStore.containsKey(treeElement)) {
