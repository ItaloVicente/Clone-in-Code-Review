    /**
     * Returns the implementation of IWorkbenchAdapter2 for the given
     * object.
     * @param o the object to look up.
     * @return IWorkbenchAdapter2 or<code>null</code> if the adapter is not defined or the
     * object is not adaptable.
     */
    protected final IWorkbenchAdapter2 getAdapter2(Object o) {
        return Adapters.adapt(o, IWorkbenchAdapter2.class);
    }
