public interface IWorkbenchWindowPulldownDelegate2 extends
        IWorkbenchWindowPulldownDelegate {
    /**
     * Returns the menu for this pull down action.  This method will only be
     * called if the user opens the pull down menu for the action.  Note that it
     * is the responsibility of the implementor to properly dispose of any SWT
     * menus created by this method.
     *
     * @return the menu
     */
    Menu getMenu(Menu parent);
