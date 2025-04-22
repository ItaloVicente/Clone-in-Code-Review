    }

    /**
     * Tests whether an action is enabled.
     */
    protected void verifyToolItemState(IAction action, boolean enabled) {
        String actionText = action.getText();
        ICoolBarManager tbm = ((WorkbenchWindow) fWindow).getCoolBarManager();
        IContributionItem[] coolItems = tbm.getItems();
        for (IContributionItem coolItem2 : coolItems) {
            if (coolItem2 instanceof ToolBarContributionItem) {
                ToolBarContributionItem coolItem = (ToolBarContributionItem) coolItem2;
                IToolBarManager citbm = coolItem.getToolBarManager();
                ToolBar tb = ((ToolBarManager) citbm).getControl();
                verifyNullToolbar(tb, actionText, citbm);
                if (tb != null && !tb.isDisposed()) {
                    ToolItem[] items = tb.getItems();
                    for (ToolItem item : items) {
                        String itemText = item.getToolTipText();
                        if (actionText.equals(itemText)) {
                            assertEquals(enabled, item.getEnabled());
                            return;
                        }
                    }
                }
            }
        }
        fail("Action for " + actionText + " not found");
    }

    /**
     * Confirms that a ToolBar is not null when you're looking a manager that
     * is a CoolItemToolBarManager and it has non-separator/non-invisible
     * contributions.
     * This is a consequence of the changes made to
     * CoolItemToolBarManager.update() that hides the a bar if it does not
     * contain anything as per the above mentioned criteria.  Under this
     * circumstance, the underlying ToolBar is not created.
     *
     * @param tb the ToolBar to check
     * @param actionText the action text
     * @param manager the IToolBarManager containing items
     * @since 3.0
     */
    private void verifyNullToolbar(ToolBar tb, String actionText,
            IToolBarManager manager) {
            IContributionItem[] items = manager.getItems();
            for (int i = 0; i < items.length; i++) {
                if (!(items[i] instanceof Separator) && items[i].isVisible()) {
                    fail("No toolbar for a visible action text \"" + actionText
                            + "\"");
                }
            }

        }
    }

    /**
     * Tests an edge case in cool bar updating when the cool bar has a single separator
     * and no other contents (or multiple separators and no other contents).
     * See bug 239945 for details.
     * @throws Throwable
     */
    public void test239945() throws Throwable {
