                setErrorMessage(entry.getErrorText());
            }
        };
    }

    /**
     * Creates a new tree item, sets the given entry or category (node)in
     * its user data field, and adds a listener to the node if it is an entry.
     *
     * @param node
     *            the entry or category associated with this item
     * @param parent
     *            the parent widget
     * @param index
     *            indicates the position to insert the item into its parent
     */
    private void createItem(Object node, Widget parent, int index) {
        TreeItem item;
        if (parent instanceof TreeItem) {
