    /**
     * Answers whether the given element is a valid selection in
     * the filtered tree.  For example, if a tree has items that
     * are categorized, the category itself may  not be a valid
     * selection since it is used merely to organize the elements.
     *
     * @param element
     * @return true if this element is eligible for automatic selection
     */
    public boolean isElementSelectable(Object element){
    	return element != null;
    }
