    /*
     * (non-Javadoc) Method declared on IAdaptable
     */
    public Object getAdapter(Class adapter) {
        if (adapter == IWorkbenchAdapter.class)
            return this;
        if (adapter == IPropertySource.class)
            return new ButtonElementProperties(this);
        return null;
    }
