					if (item.getText().contains(text))
						return true;
				return false;
			}

			public void init(SWTBot bot2) {
			}

			public String getFailureMessage() {
				return null;
			}
		}, timeout);
	}

	public static void waitUntilTreeHasNodeContainsText(SWTBot bot,
			final SWTBotTreeItem treeItem, final String text, long timeout)
			throws TimeoutException {
		bot.waitUntil(new ICondition() {

			public boolean test() throws Exception {
				for (SWTBotTreeItem item : treeItem.getItems())
					if (item.getText().contains(text))
