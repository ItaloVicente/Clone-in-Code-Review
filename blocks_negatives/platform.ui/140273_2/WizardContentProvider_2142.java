    /**
     * Adds the item to the list, unless it's a collection element without any children.
     *
     * @param element the element to test and add
     * @param list the <code>Collection</code> to add to.
     * @since 3.0
     */
    private void handleChild(Object element, ArrayList list) {
        if (element instanceof WizardCollectionElement) {
            if (hasChildren(element)) {
