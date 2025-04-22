    /**
     * The item has expanded. Updates the checked state of its children.
     */
    private void initializeItem(TreeItem item) {
        if (item.getChecked() && !item.getGrayed()) {
            updateChildrenItems(item);
        }
    }
