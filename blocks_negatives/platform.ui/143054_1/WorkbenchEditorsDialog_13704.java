        ProgressMonitorDialog pmd = new ProgressMonitorJobsDialog(getShell());
        pmd.open();
        for (TableItem item : items) {
            Adapter editor = (Adapter) item.getData();
            editor.save(pmd.getProgressMonitor());
            updateItem(item, editor);
        }
        pmd.close();
        updateItems();
    }

    /**
     * Returns all clean editors from items[];
     */
    private TableItem[] selectClean(TableItem items[]) {
        if (items.length == 0) {
