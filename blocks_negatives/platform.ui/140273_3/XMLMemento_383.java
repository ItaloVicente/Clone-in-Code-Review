    /**
     * Returns the Text node of the memento. Each memento is allowed only
     * one Text node.
     *
     * @return the Text node of the memento, or <code>null</code> if
     * the memento has no Text node.
     */
    private Text getTextNode() {
        NodeList nodes = element.getChildNodes();
        int size = nodes.getLength();
        if (size == 0) {
