        }
        return null;
    }

    /**
     * Return a tree item in the property sheet that has the same entry in
     * its user data field as the supplied entry. Return <code>null</code> if
     * there is no such item.
     *
     * @param entry
     *            the entry to search for
     * @param item
     *            the item look in
     * @return the TreeItem for the entry or <code>null</code> if
     * there isn't one.
     */
    private TreeItem findItem(IPropertySheetEntry entry, TreeItem item) {
    	Object mapItem = entryToItemMap.get(entry);
    	if (mapItem != null && mapItem instanceof TreeItem)
    		return (TreeItem) mapItem;

        if (entry == item.getData()) {
