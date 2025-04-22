    /**
     * Returns the implementation of IWorkbenchAdapter for the given
     * object.  Returns null if the adapter is not defined or the
     * object is not adaptable.
     *
     * @param element the element
     * @return the corresponding workbench adapter object
     */
    protected IWorkbenchAdapter getAdapter(Object element) {
        return Adapters.adapt(element, IWorkbenchAdapter.class);
    }
