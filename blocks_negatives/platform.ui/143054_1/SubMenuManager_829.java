        IMenuManager {

    /**
     * Maps each submenu in the manager to a wrapper.  The wrapper is used to
     * monitor additions and removals.  If the visibility of the manager is modified
     * the visibility of the submenus is also modified.
     */
    private Map<IMenuManager, SubMenuManager> mapMenuToWrapper;

    /**
     * List of registered menu listeners (element type: <code>IMenuListener</code>).
     */
