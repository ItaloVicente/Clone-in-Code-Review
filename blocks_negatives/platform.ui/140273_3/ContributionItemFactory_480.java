    /**
     * Creates a new standard contribution item for the given workbench window.
     * <p>
     * A typical contribution item automatically registers listeners against the
     * workbench window so that it can keep its enablement state up to date.
     * Ordinarily, the window's references to these listeners will be dropped
     * automatically when the window closes. However, if the client needs to get
     * rid of a contribution item while the window is still open, the client must
     * call IContributionItem#dispose to give the item an
     * opportunity to deregister its listeners and to perform any other cleanup.
     * </p>
     *
     * @param window the workbench window
     * @return the workbench contribution item
     */
    public abstract IContributionItem create(IWorkbenchWindow window);
