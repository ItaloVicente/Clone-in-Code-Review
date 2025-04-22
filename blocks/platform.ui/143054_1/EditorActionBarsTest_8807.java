	}

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

	private void verifyNullToolbar(ToolBar tb, String actionText,
			IToolBarManager manager) {
		if (tb == null) { // toolbar should only be null if the given manager is
			IContributionItem[] items = manager.getItems();
			for (IContributionItem item : items) {
				if (!(item instanceof Separator) && item.isVisible()) {
					fail("No toolbar for a visible action text \"" + actionText + "\"");
				}
			}

		}
	}

	public void test239945() throws Throwable {
