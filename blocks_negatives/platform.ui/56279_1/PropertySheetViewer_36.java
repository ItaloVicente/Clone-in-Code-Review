                item.setFont(font);
            }
        }

        updatePlus(entry, item);
    }

    /**
     * Updates the "+"/"-" icon of the tree item from the given entry
     * or category.
     *
     * @param node the entry or category
     * @param item the tree item being updated
     */
    private void updatePlus(Object node, TreeItem item) {
        IPropertySheetEntry entry = null;
        PropertySheetCategory category = null;
        if (node instanceof IPropertySheetEntry) {
