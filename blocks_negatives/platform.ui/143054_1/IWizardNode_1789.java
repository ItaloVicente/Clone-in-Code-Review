    /**
     * Returns the extent of the wizard for this node.
     * <p>
     * If the content has not yet been created, calling this method
     * does not trigger the creation of the wizard. This allows
     * this node to suggest an extent in advance of actually creating
     * the wizard.
     * </p>
     *
     * @return the extent, or <code>(-1, -1)</code> extent is not known
     */
    public Point getExtent();
