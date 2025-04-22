        if (checkDisposed()) {
            return false;
        }
        return useChevron;
    }

    /**
     * Create and display the chevron menu.
     */
    private void handleChevron(SelectionEvent event) {
        CoolItem item = (CoolItem) event.widget;
        Control control = item.getControl();
        if ((control instanceof ToolBar) == false) {
            return;
        }
        CoolBar coolBar = item.getParent();
        ToolBar toolBar = (ToolBar) control;
        Rectangle toolBarBounds = toolBar.getBounds();
        ToolItem[] items = toolBar.getItems();
        ArrayList<ToolItem> hidden = new ArrayList<>();
        for (ToolItem toolItem : items) {
            Rectangle itemBounds = toolItem.getBounds();
            if (!((itemBounds.x + itemBounds.width <= toolBarBounds.width) && (itemBounds.y
                    + itemBounds.height <= toolBarBounds.height))) {
                hidden.add(toolItem);
            }
        }

        if (chevronMenuManager != null) {
            chevronMenuManager.dispose();
        }
        chevronMenuManager = new MenuManager();
        for (ToolItem toolItem : hidden) {
            IContributionItem data = (IContributionItem) toolItem.getData();
            if (data instanceof ActionContributionItem) {
                ActionContributionItem contribution = new ActionContributionItem(
                        ((ActionContributionItem) data).getAction());
                chevronMenuManager.add(contribution);
            } else if (data instanceof SubContributionItem) {
                IContributionItem innerData = ((SubContributionItem) data)
                        .getInnerItem();
                if (innerData instanceof ActionContributionItem) {
                    ActionContributionItem contribution = new ActionContributionItem(
                            ((ActionContributionItem) innerData).getAction());
                    chevronMenuManager.add(contribution);
                }
            } else if (data.isSeparator()) {
                chevronMenuManager.add(new Separator());
            }
        }
        Menu popup = chevronMenuManager.createContextMenu(coolBar);
        Point chevronPosition = coolBar.toDisplay(event.x, event.y);
        popup.setLocation(chevronPosition.x, chevronPosition.y);
        popup.setVisible(true);
    }

    /**
     * Handles the event when the toobar item does not have its own context
     * menu.
     *
     * @param event
     *            the event object
     */
    private void handleContextMenu(Event event) {
        ToolBar toolBar = toolBarManager.getControl();
        Menu parentMenu = toolBar.getParent().getMenu();
        if ((parentMenu != null) && (!parentMenu.isDisposed())) {
            toolBar.setMenu(parentMenu);
            parentMenu.addListener(SWT.Hide, new Listener() {

                @Override
