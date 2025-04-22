        for (Iterator iter = wrappers.iterator(); iter.hasNext();) {
            EditorMenuManager element = (EditorMenuManager) iter.next();
            element.getAllContributedActions(set);
        }
    }

    protected void getAllContributedActions(HashSet set, IContributionItem item) {
        if (item instanceof MenuManager) {
