	@BeforeClass
	public static void closeWelcomePage() {
		try {
			bot.viewByTitle("Welcome").close();
		} catch (WidgetNotFoundException e) {
		}
	}

	@After
	public void resetWorkbench() {
		new Eclipse().reset();
	}

