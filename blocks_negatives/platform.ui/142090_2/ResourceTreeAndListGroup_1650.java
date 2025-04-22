                }
            });
        }
    }

    /**
     * Set the contents of the list viewer based upon the specified selected
     * tree element.  This also includes checking the appropriate list items.
     *
     * @param treeElement java.lang.Object
     */
    private void populateListViewer(final Object treeElement) {
        listViewer.setInput(treeElement);

        if (!(expandedTreeNodes.contains(treeElement))
                && whiteCheckedTreeItems.contains(treeElement)) {

            BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
                    () -> {
					    setListForWhiteSelection(treeElement);
					    listViewer.setAllChecked(true);
