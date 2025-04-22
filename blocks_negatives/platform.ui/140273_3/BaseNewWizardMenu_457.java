    /**
     * Adds the items to show to the given list.
     *
     * @param list the list to add items to
     */
    protected void addItems(List list) {
        if (addShortcuts(list)) {
            list.add(new Separator());
        }
        list.add(new ActionContributionItem(getShowDialogAction()));
    }
