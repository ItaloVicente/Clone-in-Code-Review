	private void waitForNextEnabled(final long timeoutInSec) {
		bot.waitWhile(new ICondition() {
			@Override
			public boolean test() throws Exception {
				return !bot.button("Next >").isEnabled();
			}

			@Override
			public void init(SWTBot swtBot) {
			}

			@Override
			public String getFailureMessage() {
				return "Next > button not enabled within " + timeoutInSec
						+ "sec";
			}
		}, timeoutInSec * 1000L);
	}

	private SWTBotMenu waitForMenu(final MenuFinder finder, final String label,
			final long timeoutInSec) {
		final SWTBotMenu[] menuEntry = new SWTBotMenu[] { null };
		bot.waitUntil(new ICondition() {
			@Override
			public boolean test() throws Exception {
				try {
					menuEntry[0] = finder.menu(label);
					return true;
				} catch (WidgetNotFoundException e) {
					return false;
				}
			}

			@Override
			public void init(SWTBot swtBot) {
			}

			@Override
			public String getFailureMessage() {
				return "Menu '" + label + "' not found in " + timeoutInSec
						+ "sec";
			}
		}, timeoutInSec * 1000L);
		return menuEntry[0];
	}

	private void waitForLabel(final String label, final long timeoutInSec) {
		bot.waitUntil(new ICondition() {
			@Override
			public boolean test() throws Exception {
				try {
					bot.label(label);
					return true;
				} catch (WidgetNotFoundException e) {
					return false;
				}
			}

			@Override
			public void init(SWTBot swtBot) {
			}

			@Override
			public String getFailureMessage() {
				return "Label '" + label + "' not found in " + timeoutInSec
						+ "sec";
			}
		}, timeoutInSec * 1000L);
	}

	private interface MenuFinder {
		SWTBotMenu menu(String label) throws WidgetNotFoundException;
	}
