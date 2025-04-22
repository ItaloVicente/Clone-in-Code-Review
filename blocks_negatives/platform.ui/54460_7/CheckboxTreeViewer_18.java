                    l.checkStateChanged(event);
                }
            });
        }

    }

    /**
     * Gathers the checked and grayed states of the given widget and its
     * descendents.
     *
     * @param checked a writable set of elements (element type: <code>Object</code>)
     * @param grayed a writable set of elements (element type: <code>Object</code>)
     * @param widget the widget
     */
    private void gatherState(CustomHashtable checked, CustomHashtable grayed,
            Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item instanceof TreeItem) {
                Object data = item.getData();
                if (data != null) {
                    TreeItem ti = (TreeItem) item;
                    if (ti.getChecked()) {
