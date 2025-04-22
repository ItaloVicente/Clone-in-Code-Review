        }

        return true;
    }

    /**
     *	Return a boolean indicating whether all list elements associated with
     *	the passed tree element are currently checked
     *
     *	@return boolean
     *	@param treeElement java.lang.Object
     */
    protected boolean areAllElementsChecked(Object treeElement) {
        List checkedElements = (List) checkedStateStore.get(treeElement);
        if (checkedElements == null) {
