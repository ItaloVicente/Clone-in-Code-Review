	public static SWTBotShell botForShellStartingWith(final String titlePrefix) {
		SWTWorkbenchBot bot = new SWTWorkbenchBot();

		Matcher<Shell> matcher = new TypeSafeMatcher<Shell>() {
			@Override
			protected boolean matchesSafely(Shell item) {
				String title = item.getText();
				return title != null && title.startsWith(titlePrefix);
			}

			public void describeTo(Description description) {
				description.appendText("Shell with title starting with '"
						+ titlePrefix + "'");
			}
		};

		Shell shell = bot.widget(matcher);
		return new SWTBotShell(shell);
	}

