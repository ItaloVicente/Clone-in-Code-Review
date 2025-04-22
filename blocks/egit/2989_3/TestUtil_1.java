	public static void waitUntilEditorIsActive(SWTWorkbenchBot bot,
			final SWTBotEditor editor, long timeout) {
		bot.waitUntil(new ICondition() {

			public boolean test() throws Exception {
				return editor.isActive();
			}

			public void init(SWTBot bot2) {
			}

			public String getFailureMessage() {
				return null;
			}
		}, timeout);
	}

