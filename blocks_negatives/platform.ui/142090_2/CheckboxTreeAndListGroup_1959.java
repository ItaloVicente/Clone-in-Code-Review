                }
            });
        }
    }

    /**
     *Set the contents of the list viewer based upon the specified selected
     *tree element.  This also includes checking the appropriate list items.
     *
     *@param treeElement java.lang.Object
     */
    protected void populateListViewer(final Object treeElement) {
        listViewer.setInput(treeElement);
        List listItemsToCheck = (List) checkedStateStore.get(treeElement);

        if (listItemsToCheck != null) {
            Iterator listItemsEnum = listItemsToCheck.iterator();
            while (listItemsEnum.hasNext()) {
