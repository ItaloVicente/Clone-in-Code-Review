        IPropertySheetEntry entry = (IPropertySheetEntry) treeItem.getData();
        entry.applyEditorValue();
    }

    /**
     * Creates the child items for the given widget (item or tree). This
     * method is called when the item is expanded for the first time or when an
     * item is assigned as the root of the tree.
     * @param widget TreeItem or Tree to create the children in.
     */
    private void createChildren(Widget widget) {
        TreeItem[] childItems = getChildItems(widget);

        if (childItems.length > 0) {
            Object data = childItems[0].getData();
            if (data != null) {
