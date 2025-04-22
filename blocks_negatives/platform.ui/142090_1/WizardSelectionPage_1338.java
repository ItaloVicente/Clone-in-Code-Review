        return wizard.getStartingPage();
    }

    /**
     * Returns the currently selected wizard node within this page.
     *
     * @return the wizard node, or <code>null</code> if no node is selected
     */
    public IWizardNode getSelectedNode() {
        return selectedNode;
    }

    /**
     * Sets or clears the currently selected wizard node within this page.
     *
     * @param node the wizard node, or <code>null</code> to clear
     */
    protected void setSelectedNode(IWizardNode node) {
        addSelectedNode(node);
        selectedNode = node;
        if (isCurrentPage()) {
