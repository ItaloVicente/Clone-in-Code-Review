    /**
     *	Set the checked state of self and all ancestors appropriately
     */
    protected void updateHierarchy(Object treeElement) {

        boolean whiteChecked = determineShouldBeWhiteChecked(treeElement);
        boolean shouldBeAtLeastGray = determineShouldBeAtLeastGrayChecked(treeElement);

        treeViewer.setChecked(treeElement, shouldBeAtLeastGray);
        setWhiteChecked(treeElement, whiteChecked);
        if (whiteChecked) {
			treeViewer.setGrayed(treeElement, false);
		} else {
			treeViewer.setGrayed(treeElement, shouldBeAtLeastGray);
		}

        Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
            grayUpdateHierarchy(parent);
        }
    }

