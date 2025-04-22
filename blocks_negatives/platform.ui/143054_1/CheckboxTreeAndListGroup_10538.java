    }

    /**
     *	Set the initial checked state of the passed list element to true.
     *
     *	@param element the element in the list to select
     */
    public void initialCheckListItem(Object element) {
        Object parent = treeContentProvider.getParent(element);
        currentTreeSelection = parent;
        listItemChecked(element, true, false);
        updateHierarchy(parent);
    }

    /**
     *	Set the initial checked state of the passed element to true,
     *	as well as to all of its children and associated list elements
     *
     *	@param element the element in the tree to select
     */
    public void initialCheckTreeItem(Object element) {
        treeItemChecked(element, true);
    }

    /**
     *	Initialize this group's viewers after they have been laid out.
     */
    protected void initialize() {
        treeViewer.setInput(root);
    }

    /**
     *	Callback that's invoked when the checked status of an item in the list
     *	is changed by the user. Do not try and update the hierarchy if we are building the
     *  initial list.
     */
    protected void listItemChecked(Object listElement, boolean state,
            boolean updatingFromSelection) {
        List checkedListItems = (List) checkedStateStore
                .get(currentTreeSelection);

        if (state) {
            if (checkedListItems == null) {
                grayCheckHierarchy(currentTreeSelection);
                checkedListItems = (List) checkedStateStore
                        .get(currentTreeSelection);
            }
            checkedListItems.add(listElement);
        } else {
            checkedListItems.remove(listElement);
            if (checkedListItems.isEmpty()) {
                ungrayCheckHierarchy(currentTreeSelection);
            }
        }

        if (updatingFromSelection) {
