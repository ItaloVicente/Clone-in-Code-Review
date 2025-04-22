	public static void waitUntilTreeHasNodeWithText(SWTBot bot, final SWTBotTree tree,
			final String text, long timeout) throws TimeoutException {
		bot.waitUntil(new ICondition() {

			public boolean test() throws Exception {
				for (SWTBotTreeItem item : tree.getAllItems())
					if (item.getText().startsWith(text))
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

	public static void waitUntilTableHasRowWithText(SWTBot bot, final SWTBotTable table,
			final String text, long timeout) throws TimeoutException {
		bot.waitUntil(new ICondition() {

			public boolean test() throws Exception {
				if (table.indexOf(text)<0)
					return false;
				return true;
			}

			public void init(SWTBot bot2) {
			}

			public String getFailureMessage() {
				return null;
			}
		}, timeout);
	}

