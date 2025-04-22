	public static void waitUntilTreeHasSelectedNodeWithText(SWTBot bot,
			final SWTBotTree tree, final String text, long timeout)
			throws TimeoutException {
		bot.waitUntil(new ICondition() {

			public boolean test() throws Exception {
				return tree.selection().get(0, 0).equals(text);
			}

			public void init(SWTBot bot2) {
			}

			public String getFailureMessage() {
				return null;
			}
		}, timeout);
	}

