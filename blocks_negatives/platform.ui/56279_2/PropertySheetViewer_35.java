                updateCategory((PropertySheetCategory) el, childItems[i]);
                updateChildrenOf(el, childItems[i]);
            }
        }
        entrySelectionChanged();
    }

    /**
     * Update the given entry (but not its children or parent)
     *
     * @param entry
     *            the entry we will update
     * @param item
     *            the tree item for the given entry
     */
    private void updateEntry(IPropertySheetEntry entry, TreeItem item) {
        item.setData(entry);

        entryToItemMap.put(entry, item);

        item.setText(0, entry.getDisplayName());
        item.setText(1, entry.getValueAsString());
        Image image = entry.getImage();
        if (item.getImage(1) != image) {
            item.setImage(1, image);
        }

        if (entry instanceof PropertySheetEntry) {
            PropertySheetEntry entry2 = (PropertySheetEntry) entry;

            Color color = entry2.getForeground();
