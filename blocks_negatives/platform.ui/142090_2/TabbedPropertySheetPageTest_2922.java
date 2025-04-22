    /**
     * Set the selection in the view to cause the properties view to change.
     *
     * @param selectedNodes
     *            nodes to select in the view.
     */
    private void setSelection(TreeNode[] selectedNodes) {
        StructuredSelection selection = new StructuredSelection(selectedNodes);
        testsView.getViewer().setSelection(selection, true);
    }
