    protected abstract Composite createTreeViewer(Composite parent);

    /**
     * Method to call when an item in one of the lists is double-clicked.
     * Shows the first page of the selected wizard or expands a collapsed
     * tree.
     * @param event
     */
    protected void treeDoubleClicked(DoubleClickEvent event){
    	ISelection selection = event.getViewer().getSelection();
	    IStructuredSelection ss = (IStructuredSelection) selection;
    	listSelectionChanged(ss);
