    /**
     * Returns an array of editor history items.  The items are returned in order
     * of most recent first.
     */
    public EditorHistoryItem[] getItems() {
        refresh();
        EditorHistoryItem[] array = new EditorHistoryItem[fifoList.size()];
        fifoList.toArray(array);
        return array;
    }
