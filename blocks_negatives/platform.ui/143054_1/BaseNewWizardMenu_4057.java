    /**
     * Adds the new wizard shortcuts for the current perspective to the given list.
     *
     * @param list the list to add items to
     * @return <code>true</code> if any items were added, <code>false</code> if none were added
     */
    protected boolean addShortcuts(List list) {
        boolean added = false;
        IWorkbenchPage page = workbenchWindow.getActivePage();
        if (page != null) {
            String[] wizardIds = page.getNewWizardShortcuts();
            for (String wizardId : wizardIds) {
                IAction action = getAction(wizardId);
                if (action != null) {
                    if (!WorkbenchActivityHelper.filterItem(action)) {
                        list.add(new ActionContributionItem(action));
                        added = true;
                    }
                }
            }
        }
        return added;
    }
