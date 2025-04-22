    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.gmf.runtime.common.ui.action.ActionMenuManager which is
     * an implementation of an <code>IMenuManager</code> that inherits its UI
     * (text + icon + hints) from a given action. When filled in a toolbar, the
     * menu is rendered as a tool item.
     *
     * See Bug 410426 which corrects a ClassCastException in
     * ToolbarManagerRenderer while looking for a IContributionItem parent ToolBarManager.
     */
