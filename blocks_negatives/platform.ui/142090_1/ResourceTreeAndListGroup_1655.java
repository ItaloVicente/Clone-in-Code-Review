            }
        }
    }

    /**
     *	Set the tree viewer's providers to those passed
     *
     *	@param contentProvider ITreeContentProvider
     *	@param labelProvider ILabelProvider
     */
    public void setTreeProviders(ITreeContentProvider contentProvider, ILabelProvider labelProvider) {
        List<?> items;
        if (root == null) {
            items = Collections.emptyList();
        } else {
            items = getAllWhiteCheckedItems();
            for (Object object : items) {
                setTreeChecked(object, false);
            }
        }

        treeViewer.setContentProvider(contentProvider);
        treeViewer.setLabelProvider(labelProvider);
        treeContentProvider = contentProvider;
        treeLabelProvider = labelProvider;

        for (Object object : items) {
            setTreeChecked(object, true);
        }
    }

    /**
     * Set the comparator that is to be applied to self's tree viewer
     *
     * @param comparator the comparator for the tree
     */
    public void setTreeComparator(ViewerComparator comparator) {
        treeViewer.setComparator(comparator);
    }

    /**
     *	Adjust the collection of references to white-checked tree elements appropriately.
     *
     *	@param treeElement java.lang.Object
     *	@param isWhiteChecked boolean
     */
    private void setWhiteChecked(Object treeElement, boolean isWhiteChecked) {
        if (isWhiteChecked) {
            if (!whiteCheckedTreeItems.contains(treeElement)) {
