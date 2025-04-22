    }

    /**
     * Returns the implementation of IWorkbenchAdapter for the given
     * object.  Returns <code>null</code> if the adapter is not defined or the
     * object is not adaptable.
     */
    protected final IWorkbenchAdapter getAdapter(Object o) {
        if (!(o instanceof IAdaptable)) {
            return null;
        }
