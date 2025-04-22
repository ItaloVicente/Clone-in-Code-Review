        deferWizards.add(element);
    }

    /**
     * Finishes the addition of categories.  The categories are sorted and
     * added in a root to depth traversal.
     */
    private void finishCategories() {
        if (deferCategories == null) {
