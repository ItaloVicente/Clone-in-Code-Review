    /**
     * Returns the intro part. Returns <code>null</code> if there is no intro
     * part, if it has been previously closed via {@link #closeIntro(IIntroPart)}
     * or if there is an intro part but {@link #showIntro(IWorkbenchWindow, boolean)}
     * has not yet been called to create it.
     *
     * @return the intro part, or <code>null</code> if none is available
     */
    public IIntroPart getIntro();
