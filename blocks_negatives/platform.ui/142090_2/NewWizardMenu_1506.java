    private boolean enabled = true;

    /**
     * Creates a new wizard shortcut menu for the IDE.
     * <p>
     * <strong>Note:</strong> Clients must dispose this menu when it is no longer required.
     * </p>
     *
     * @param window
     *            the window containing the menu
     */
    public NewWizardMenu(IWorkbenchWindow window) {
        this(window, null);

    }

    /**
     * Creates a new wizard shortcut menu for the IDE.
     * <p>
     * <strong>Note:</strong> Clients must dispose this menu when it is no longer required.
     * </p>
     *
     * @param window
     *            the window containing the menu
     * @param id
     *            the identifier for this contribution item
     */
    public NewWizardMenu(IWorkbenchWindow window, String id) {
        super(window, id);
        newExampleAction = new NewExampleAction(window);
        newProjectAction = new NewProjectAction(window);
    }

    /**
     * Create a new wizard shortcut menu.
     * <p>
     * If the menu will appear on a semi-permanent basis, for instance within
     * a toolbar or menubar, the value passed for <code>register</code> should be true.
     * If set, the menu will listen to perspective activation and update itself
     * to suit.  In this case clients are expected to call <code>deregister</code>
     * when the menu is no longer needed.  This will unhook any perspective
     * listeners.
     * </p>
     * <p>
     * <strong>Note:</strong> Clients must dispose this menu when it is no longer required.
     * </p>
     *
     * @param innerMgr the location for the shortcut menu contents
     * @param window the window containing the menu
     * @param register if <code>true</code> the menu listens to perspective changes in
     *      the window
     * @deprecated use NewWizardMenu(IWorkbenchWindow) instead
     */
    @Deprecated
