    /**
     * Returns all preference nodes managed by this
     * manager.
     *
     * @param order the traversal order, one of
     *	<code>PRE_ORDER</code> and <code>POST_ORDER</code>
     * @return a list of preference nodes
     *  (element type: <code>IPreferenceNode</code>)
     *  in the given order
     */
    public List<IPreferenceNode> getElements(int order) {
        Assert.isTrue(order == PRE_ORDER || order == POST_ORDER,
        ArrayList<IPreferenceNode> sequence = new ArrayList<>();
        IPreferenceNode[] subnodes = getRoot().getSubNodes();
        for (IPreferenceNode subnode : subnodes) {
