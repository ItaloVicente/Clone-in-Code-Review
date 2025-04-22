    /**
     * Called by a job run in a separate thread to fetch the children of this adapter.
     * The adapter should in return notify of new children via the collector.
     * This is generally used when a content provider is getting elements.
     * <p>
     * It is good practice to check the passed in monitor for cancellation. This will
     * provide good responsiveness for cancellation requests made by the user.
     * </p>
     *
     * @param object the object to fetch the children for
     * @param collector the collector to notify about new children. Should not
     * 		be <code>null</code>.
     * @param  monitor a progress monitor that will never be <code>null<code> to
     *                   support reporting and cancellation.
     */
    public void fetchDeferredChildren(Object object,
            IElementCollector collector, IProgressMonitor monitor);
