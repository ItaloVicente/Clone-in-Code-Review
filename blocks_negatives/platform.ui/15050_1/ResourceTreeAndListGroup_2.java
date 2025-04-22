    /**
     *	Handle the collapsing of an element in a tree viewer
     */
    public void treeCollapsed(TreeExpansionEvent event) {
    }

    /**
     *	Handle the expansionsion of an element in a tree viewer
     */
    public void treeExpanded(TreeExpansionEvent event) {
        expandTreeElement(event.getElement());
    }

