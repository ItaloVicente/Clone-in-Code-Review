    /**
     * Allocates a new DrillDownTreePart.
     *
     * @param tree the target tree for refocusing
     */
    public DrillDownAdapter(TreeViewer tree) {
        fDrillStack = new DrillStack();
        fChildTree = tree;
    }
