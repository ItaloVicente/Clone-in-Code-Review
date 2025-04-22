    
    /**
     * Returns the working set icon.
     * Currently, this is one of the icons specified in the extensions 
     * of the org.eclipse.ui.workingSets extension point. 
     * The extension is identified using the value returned by
     * <code>getId()</code>. 
     * Returns <code>null</code> if no icon has been specified in the 
     * extension or if <code>getId()</code> returns <code>null</code>. 
     * 
     * @return the working set icon or <code>null</code>.
     * @since 3.3
     */
    public ImageDescriptor getImageDescriptor();

    /**
     * Returns the name of the working set.
     * 
     * @return	the name of the working set
     */
    public String getName();

    /**
