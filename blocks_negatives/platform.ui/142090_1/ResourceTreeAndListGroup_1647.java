    }

    /**
     *	Set the checked state of self and all ancestors appropriately. Do not white check anyone - this is
     *  only done down a hierarchy.
     */
    private void grayUpdateHierarchy(Object treeElement) {
        boolean shouldBeAtLeastGray = determineShouldBeAtLeastGrayChecked(treeElement);
        treeViewer.setGrayChecked(treeElement, shouldBeAtLeastGray);
        if (whiteCheckedTreeItems.contains(treeElement)) {
