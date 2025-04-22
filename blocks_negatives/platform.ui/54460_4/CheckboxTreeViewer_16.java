     * Applies the checked and grayed states of the given widget and its
     * descendents.
     *
     * @param checked a set of elements (element type: <code>Object</code>)
     * @param grayed a set of elements (element type: <code>Object</code>)
     * @param widget the widget
     */
    private void applyState(CustomHashtable checked, CustomHashtable grayed,
            Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item instanceof TreeItem) {
                Object data = item.getData();
                if (data != null) {
                    TreeItem ti = (TreeItem) item;
                    ti.setChecked(checked.containsKey(data));
                    ti.setGrayed(grayed.containsKey(data));
                }
            }
            applyState(checked, grayed, item);
        }
    }

    /**
     * Notifies any check state listeners that the check state of an element has changed.
     * Only listeners registered at the time this method is called are notified.
     *
     * @param event a check state changed event
     *
     * @see ICheckStateListener#checkStateChanged
     */
    protected void fireCheckStateChanged(final CheckStateChangedEvent event) {
        Object[] array = checkStateListeners.getListeners();
        for (int i = 0; i < array.length; i++) {
            final ICheckStateListener l = (ICheckStateListener) array[i];
            SafeRunnable.run(new SafeRunnable() {
                @Override
