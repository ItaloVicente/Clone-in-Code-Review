        editorsTable.setFocus();
        applyDialogFont(dialogArea);
        return dialogArea;
    }

    /**
     * Updates the button state (enabled/disabled)
     */
    private void updateButtons() {
        TableItem selectedItems[] = editorsTable.getSelection();
        boolean hasDirty = false;
        for (TableItem selectedItem : selectedItems) {
            Adapter editor = (Adapter) selectedItem.getData();
            if (editor.isDirty()) {
                hasDirty = true;
                break;
            }
        }
        saveSelected.setEnabled(hasDirty);

        TableItem allItems[] = editorsTable.getItems();
        boolean hasClean = false;
        for (TableItem tableItem : allItems) {
            Adapter editor = (Adapter) tableItem.getData();
            if (!editor.isDirty()) {
                hasClean = true;
                break;
            }
        }
        selectClean.setEnabled(hasClean);
        invertSelection.setEnabled(allItems.length > 0);
        closeSelected.setEnabled(selectedItems.length > 0);

        Button ok = getOkButton();
        if (ok != null) {
