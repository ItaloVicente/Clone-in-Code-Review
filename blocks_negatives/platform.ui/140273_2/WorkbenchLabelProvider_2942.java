    /**
     * Returns the implementation of IWorkbenchAdapter for the given
     * object.
     * @param o the object to look up.
     * @return IWorkbenchAdapter or<code>null</code> if the adapter is not defined or the
     * object is not adaptable.
     */
    protected final IWorkbenchAdapter getAdapter(Object o) {
        return Adapters.adapt(o, IWorkbenchAdapter.class);
    }
