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
