    /**
     * Creates the SWT controls for this workbench part.
     * <p>
     * Clients should not call this method (the workbench calls this method when
     * it needs to, which may be never).
     * </p>
     * <p>
     * For implementors this is a multi-step process:
     * <ol>
     *   <li>Create one or more controls within the parent.</li>
     *   <li>Set the parent layout as needed.</li>
     *   <li>Register any global actions with the site's <code>IActionBars</code>.</li>
     *   <li>Register any context menus with the site.</li>
     *   <li>Register a selection provider with the site, to make it available to
     *     the workbench's <code>ISelectionService</code> (optional). </li>
     * </ol>
     * </p>
     *
     * @param parent the parent control
     */
    public void createPartControl(Composite parent);
