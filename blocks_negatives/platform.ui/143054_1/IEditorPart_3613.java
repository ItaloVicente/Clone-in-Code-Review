    /**
     * Initializes this editor with the given editor site and input.
     * <p>
     * This method is automatically called shortly after the part is instantiated.
     * It marks the start of the part's lifecycle. The
     * {@link IWorkbenchPart#dispose IWorkbenchPart.dispose} method will be called
     * automically at the end of the lifecycle. Clients must not call this method.
     * </p><p>
     * Implementors of this method must examine the editor input object type to
     * determine if it is understood.  If not, the implementor must throw
     * a <code>PartInitException</code>
     * </p>
     * @param site the editor site
     * @param input the editor input
     * @exception PartInitException if this editor was not initialized successfully
     */
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException;
