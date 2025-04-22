    /**
     * Shows the intro part in the given workbench window. If the intro part has
     * not been created yet, one will be created. If the intro part is currently
     * being shown in some workbench window, that other window is made active.
     *
     * @param preferredWindow the preferred workbench window, or
     * <code>null</code> to indicate the currently active workbench window
     * @param standby <code>true</code> to put the intro part in its partially
     * visible standy mode, and <code>false</code> to make it fully visible
     * @return the newly-created or existing intro part, or <code>null</code>
     * if no intro part is available or if <code>preferredWindow</code> is
     * <code>null</code> and there is no currently active workbench window
     */
    public IIntroPart showIntro(IWorkbenchWindow preferredWindow,
            boolean standby);
