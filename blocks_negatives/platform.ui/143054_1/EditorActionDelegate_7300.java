    /**
     * The <code>EditorActionDelegate</code> implementation of this
     * <code>IActionDelegate</code> method does nothing.
     *
     * Selection in the workbench has changed. Plugin provider
     * can use it to change the availability of the action
     * or to modify other presentation properties.
     *
     * <p>Action delegate cannot be notified about
     * selection changes before it is loaded. For that reason,
     * control of action's enable state should also be performed
     * through simple XML rules defined for the extension
     * point. These rules allow enable state control before
     * the delegate has been loaded.</p>
     */
    @Override
