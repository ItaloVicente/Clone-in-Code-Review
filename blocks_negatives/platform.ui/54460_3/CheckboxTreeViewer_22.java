    }

    /**
     * Gathers the checked states of the given widget and its
     * descendents, following a pre-order traversal of the tree.
     *
     * @param result a writable list of elements (element type: <code>Object</code>)
     * @param widget the widget
     */
    private void internalCollectChecked(List result, Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item instanceof TreeItem && ((TreeItem) item).getChecked()) {
                Object data = item.getData();
                if (data != null) {
