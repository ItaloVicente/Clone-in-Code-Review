        }

        return false;
    }

    /**
     * Returns a boolean indicating whether the passed tree item should be
     * white-checked.
     *
     * @return boolean
     * @param treeElement java.lang.Object
     */
    protected boolean determineShouldBeWhiteChecked(Object treeElement) {
        return areAllChildrenWhiteChecked(treeElement)
                && areAllElementsChecked(treeElement);
    }

    /**
     *	Recursively add appropriate tree elements to the collection of
     *	known white-checked tree elements.
     *
     *	@param treeElement java.lang.Object
     */
    protected void determineWhiteCheckedDescendents(Object treeElement) {
