    /**
     * Creates the initial layout for a page.
     * <p>
     * Implementors of this method may add additional views to a
     * perspective.  The perspective already contains an editor folder
     * identified by the result of <code>IPageLayout.getEditorArea()</code>.
     * Additional views should be added to the layout using this value as
     * the initial point of reference.
     * </p>
     *
     * @param layout the page layout
     */
    void createInitialLayout(IPageLayout layout);
