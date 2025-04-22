        TreeViewer viewer = getTreeViewer();
        ViewerSorter viewerSorter = viewer.getSorter();

        viewer.getControl().setRedraw(false);
        if (viewerSorter == sorter) {
            viewer.refresh();
        } else {
            viewer.setSorter(sorter);
        }
        viewer.getControl().setRedraw(true);
        settings.put(STORE_SORT_TYPE, sorter.getCriteria());

        updateActionBars((IStructuredSelection) viewer.getSelection());
    }

    /**
     * Sets the resource comparator
     *
     * @param comparator the resource comparator
     * @since 3.3
     */
    @Override
	public void setComparator(ResourceComparator comparator){
        TreeViewer viewer = getTreeViewer();
        ViewerComparator viewerComparator = viewer.getComparator();

        viewer.getControl().setRedraw(false);
        if (viewerComparator == comparator) {
            viewer.refresh();
        } else {
            viewer.setComparator(comparator);
        }
        viewer.getControl().setRedraw(true);
        settings.put(STORE_SORT_TYPE, comparator.getCriteria());

