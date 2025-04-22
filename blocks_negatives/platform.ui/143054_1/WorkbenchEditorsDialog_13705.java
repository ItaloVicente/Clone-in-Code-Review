        }
        TableItem result[] = new TableItem[cleanItems.size()];
        cleanItems.toArray(result);
        return result;
    }

    /**
     * Returns all clean editors from items[];
     */
    private TableItem[] invertedSelection(TableItem allItems[],
            TableItem selectedItems[]) {
        if (allItems.length == 0) {
