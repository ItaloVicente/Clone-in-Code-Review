    /**
     * Expands the given items in the tree.  The list of items passed should be
     * derived by calling <code>getExpanded</code>.
     *
     * @param items is a list of items within the tree which should be expanded
     */
    private void expand(List items) {
        fChildTree.setExpandedElements(items.toArray());
    }
