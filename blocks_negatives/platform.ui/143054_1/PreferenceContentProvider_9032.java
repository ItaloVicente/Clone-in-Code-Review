    /**
     * Find the parent of the provided node.  Will search recursivly through the
     * preference tree.
     *
     * @param parent the possible parent node.
     * @param target the target child node.
     * @return the parent node of the child node.
     */
    private IPreferenceNode findParent(IPreferenceNode parent,
            IPreferenceNode target) {
        if (parent.getId().equals(target.getId())) {
