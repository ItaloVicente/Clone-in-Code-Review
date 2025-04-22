    /**
     * Gets the status line contribution item which the key binding
     * architecture uses to keep the user up-to-date as to the current state.
     *
     * @return The status line contribution item, if any; <code>null</code>,
     *         if none.
     */
    StatusLineContributionItem getStatusLine() {
        if (associatedWindow instanceof WorkbenchWindow) {
            WorkbenchWindow window = (WorkbenchWindow) associatedWindow;
            IStatusLineManager statusLine = window.getStatusLineManager();
                IContributionItem item = statusLine
                if (item instanceof StatusLineContributionItem) {
                    return ((StatusLineContributionItem) item);
                }
            }
        }
