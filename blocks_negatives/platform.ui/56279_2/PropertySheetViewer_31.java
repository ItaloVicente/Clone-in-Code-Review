        ArrayList categoryList = new ArrayList();
        Set seen = new HashSet(childEntries.size());
        for (int i = 0; i < childEntries.size(); i++) {
            IPropertySheetEntry childEntry = (IPropertySheetEntry) childEntries
                    .get(i);
            String categoryName = childEntry.getCategory();
            if (categoryName != null && !seen.contains(categoryName)) {
                seen.add(categoryName);
                PropertySheetCategory category = (PropertySheetCategory) categoryCache
                        .get(categoryName);
                if (category != null) {
                    categoryList.add(category);
                }
            }
        }
        if (addMisc && !seen.contains(MISCELLANEOUS_CATEGORY_NAME)) {
            categoryList.add(misc);
        }

        PropertySheetCategory[] categoryArray = (PropertySheetCategory[]) categoryList
        	.toArray(new PropertySheetCategory[categoryList.size()]);
        sorter.sort(categoryArray);
        categories = categoryArray;
    }

    /**
     * Update the category (but not its parent or children).
     *
     * @param category
     *            the category to update
     * @param item
     *            the tree item for the given entry
     */
    private void updateCategory(PropertySheetCategory category,
            TreeItem item) {
        item.setData(category);

        entryToItemMap.put(category, item);

        item.setText(0, category.getCategoryName());
        item.setText(1, ""); //$NON-NLS-1$

        if (category.getAutoExpand()) {
            createChildren(item);
            item.setExpanded(true);
            category.setAutoExpand(false);
        } else {
            updatePlus(category, item);
        }
    }

    /**
     * Update the child entries or categories of the given entry or category. If
     * the given node is the root entry and we are showing categories then the
     * child entries are categories, otherwise they are entries.
     *
     * @param node
     *            the entry or category whose children we will update
     * @param widget
     *            the widget for the given entry, either a
     *            <code>TableTree</code> if the node is the root node or a
     *            <code>TreeItem</code> otherwise.
     */
    private void updateChildrenOf(Object node, Widget widget) {
        IPropertySheetEntry entry = null;
        PropertySheetCategory category = null;
        if (node instanceof IPropertySheetEntry) {
