        final MenuManager manager = new MenuManager();
        fillMenu(manager);
        final IContributionItem items[] = manager.getItems();
        if (items.length == 0) {
            MenuItem item = new MenuItem(menu, SWT.NONE, index++);
            item.setText(NO_TARGETS_MSG);
            item.setEnabled(false);
        } else {
            for (IContributionItem item : items) {
                item.fill(menu, index++);
            }
        }
        dirty = false;
    }

    /**
     * Fills the given menu manager with all the open perspective actions
     * appropriate for the currently active perspective. Filtering is applied to
     * the actions based on the activities and capabilities mechanism.
     *
     * @param manager
     *            The menu manager that should receive the menu items; must not
     *            be <code>null</code>.
     * @since 3.1
     */
    private final void fillMenu(final MenuManager manager) {
        manager.removeAll();

