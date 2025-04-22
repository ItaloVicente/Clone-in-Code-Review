        Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
            grayUpdateHierarchy(parent);
        }
    }

    /**
     *	Set the initial checked state of the passed list element to true.
     * @param element
     */
    public void initialCheckListItem(Object element) {
        Object parent = treeContentProvider.getParent(element);
        selectAndReveal(parent);
        listViewer.setChecked(element, true);
        listItemChecked(element, true, false);
        grayUpdateHierarchy(parent);
    }

    /**
     * Set the initial checked state of the passed element to true,
     * as well as to all of its children and associated list elements
     * @param element
     */
    public void initialCheckTreeItem(Object element) {
        treeItemChecked(element, true);
        selectAndReveal(element);
    }

    private void selectAndReveal(Object treeElement) {
        treeViewer.reveal(treeElement);
        IStructuredSelection selection = new StructuredSelection(treeElement);
        treeViewer.setSelection(selection);
    }

    /**
     *	Initialize this group's viewers after they have been laid out.
     */
    private void initialize() {
        treeViewer.setInput(root);
        this.expandedTreeNodes = new HashSet<>();
        this.expandedTreeNodes.add(root);

    }

    /**
     *	Callback that's invoked when the checked status of an item in the list
     *	is changed by the user. Do not try and update the hierarchy if we are building the
     *  initial list.
     */
    private void listItemChecked(Object listElement, boolean state, boolean updatingFromSelection) {
        List checkedListItems = (List) checkedStateStore.get(currentTreeSelection);
        if (!expandedTreeNodes.contains(currentTreeSelection)) {
