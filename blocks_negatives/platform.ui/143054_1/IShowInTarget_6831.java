    /**
     * Shows the given context in this target.
     * The target should check the context's selection for elements
     * to show.  If there are no relevant elements in the selection,
     * then it should check the context's input.
     *
     * @param context the context to show
     * @return <code>true</code> if the context could be shown,
     *   <code>false</code> otherwise
     */
    public boolean show(ShowInContext context);
