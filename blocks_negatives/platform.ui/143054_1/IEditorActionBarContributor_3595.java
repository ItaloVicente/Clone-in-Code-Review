    /**
     * Initializes this contributor, which is expected to add contributions as
     * required to the given action bars and global action handlers.
     * <p>
     * The page is passed to support the use of <code>RetargetAction</code> by
     * the contributor. In this case the init method implementors should:
     * </p>
     * <p><ul>
     * <li>1) set retarget actions as global action handlers</li>
     * <li>2) add the retarget actions as part listeners</li>
     * <li>3) get the active part and if not <code>null</code>
     * call partActivated on the retarget actions</li>
     * </ul></p>
     * <p>
     * And in the dispose method the retarget actions should be removed as part listeners.
     * </p>
     *
     * @param bars the action bars
     * @param page the workbench page for this contributor
     * @since 2.0
     */
    public void init(IActionBars bars, IWorkbenchPage page);
