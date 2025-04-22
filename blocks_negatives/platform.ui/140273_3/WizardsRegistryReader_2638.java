        otherCategory.add(element);
        element.setParent(otherCategory);
    }

    /**
     * Removes the empty categories from a wizard collection.
     */
    private void pruneEmptyCategories(WizardCollectionElement parent) {
        Object[] children = parent.getChildren(null);
        for (Object element : children) {
            WizardCollectionElement child = (WizardCollectionElement) element;
            pruneEmptyCategories(child);
            boolean shouldPrune = child.getId().equals(FULL_EXAMPLES_WIZARD_CATEGORY);
            if (child.isEmpty() && shouldPrune) {
