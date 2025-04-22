    /**
     * Return a view descriptor with the given extension id.  If no view exists
     * with the id return <code>null</code>.
     * Will also return <code>null</code> if the view descriptor exists, but
     * is filtered by an expression-based activity.
     *
     * @param id the id to search for
     * @return the descriptor or <code>null</code>
     */
    IViewDescriptor find(String id);
