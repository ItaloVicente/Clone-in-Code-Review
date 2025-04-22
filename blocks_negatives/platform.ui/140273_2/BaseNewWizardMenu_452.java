    /**
     * Creates a new wizard shortcut menu for the IDE.
     * <p>
     * <strong>Note:</strong> Clients must dispose this menu when it is no longer required.
     * </p>
     *
     * @param window
     *            the window containing the menu
     * @param id
     *            the contribution item identifier, or <code>null</code>
     */
    public BaseNewWizardMenu(IWorkbenchWindow window, String id) {
        super(id);
        Assert.isNotNull(window);
        this.workbenchWindow = window;
        showDlgAction = ActionFactory.NEW.create(window);
        registerListeners();
