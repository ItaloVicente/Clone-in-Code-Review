        String id = path;
        String rest = null;
        int separator = path.indexOf('/');
        if (separator != -1) {
            id = path.substring(0, separator);
            rest = path.substring(separator + 1);
        } else {
            return super.find(path);
        }

        IContributionItem item = super.find(id);
        if (item instanceof IMenuManager) {
            IMenuManager manager = (IMenuManager) item;
            return manager.findUsingPath(rest);
        }
        return null;
    }

    /**
     * Notifies any menu listeners that a menu is about to show.
     * Only listeners registered at the time this method is called are notified.
     *
     * @param manager the menu manager
     *
     * @see IMenuListener#menuAboutToShow
     */
    private void fireAboutToShow(IMenuManager manager) {
