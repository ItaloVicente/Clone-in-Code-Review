    /**
     * Recursively enumerates all nodes at or below the given node
     * and adds them to the given list in the given order.
     *
     * @param node the starting node
     * @param sequence a read-write list of preference nodes
     *  (element type: <code>IPreferenceNode</code>)
     *  in the given order
     * @param order the traversal order, one of
     *	<code>PRE_ORDER</code> and <code>POST_ORDER</code>
     */
    protected void buildSequence(IPreferenceNode node, List<IPreferenceNode> sequence, int order) {
        if (order == PRE_ORDER) {
