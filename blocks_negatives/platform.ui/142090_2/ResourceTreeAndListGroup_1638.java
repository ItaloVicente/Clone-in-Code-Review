            }

        }
    }

    /**
     * Find all of the white checked children of the treeElement and add them to the collection.
     * If the element itself is white select add it. If not then add any selected list elements
     * and recurse down to the children.
     * @param treeElement java.lang.Object
     * @param result java.util.Collection
     */
    private void findAllWhiteCheckedItems(Object treeElement, Collection result) {
        if (whiteCheckedTreeItems.contains(treeElement)) {
