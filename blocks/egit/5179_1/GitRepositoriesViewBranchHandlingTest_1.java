
	private void assertCheckoutNotAvailable(final SWTBotView view) {
		assertFalse("Checkout context menu item should not exist",
				ContextMenuHelper.contextMenuItemExists(view.bot().tree(),
						myUtil.getPluginLocalizedValue("CheckoutCommand")));
	}

