        return null;
    }

    /**
     * Get the trigger point for the wizard type, if one exists.
     *
     * @return the wizard's trigger point
     */
    protected ITriggerPoint getTriggerPoint(){
    }

    /**
     * Set the tree viewer that is used for this wizard selection page.
     *
     * @param viewer
     */
    protected void setTreeViewer(TreeViewer viewer){
    	treeViewer = viewer;
    }

    /**
     * Get the tree viewer that is used for this wizard selection page.
     *
     * @return tree viewer used for this wizard's selection page
     */
    protected TreeViewer getTreeViewer(){
    	return treeViewer;
    }

    /**
     * Perform any initialization of the wizard page that needs to be done
     * after widgets are created and main control is set.
     */
    protected void initialize(){
    }
