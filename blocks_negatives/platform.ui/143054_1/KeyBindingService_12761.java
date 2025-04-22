    /**
     * Constructs a new instance of <code>KeyBindingService</code> on a given
     * workbench site. This instance is not nested.
     *
     * @param workbenchPartSite
     *            The site for which this service will be responsible; should
     *            not be <code>null</code>.
     */
    public KeyBindingService(IWorkbenchPartSite workbenchPartSite) {
        this(workbenchPartSite, null);
    }

    /**
     * Constructs a new instance of <code>KeyBindingService</code> on a given
     * workbench site.
     *
     * @param workbenchPartSite
     *            The site for which this service will be responsible; should
     *            not be <code>null</code>.
     * @param parent
     *            The parent key binding service, if any; <code>null</code> if
     *            none.
     */
    KeyBindingService(IWorkbenchPartSite workbenchPartSite,
            KeyBindingService parent) {
        this.workbenchPartSite = workbenchPartSite;
