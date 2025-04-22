	private static void closeWelcomePage() {
		if (welcomePageClosed)
			return;
		try {
			bot.viewByTitle("Welcome").close();
		} catch (WidgetNotFoundException e) {
		} finally {
			welcomePageClosed = true;
		}
	}

	@Before
	public void setBotAndAtivateShell() {
		SWTBotShell[] shells = bot.shells();
		for (SWTBotShell shell : shells) {
			if (isEclipseShell(shell)) {
				shell.activate();
				return;
			}
		}
		fail("No active Eclipse shell found!");
	}

	@After
	public void closeShells() {
		SWTBotShell[] shells = bot.shells();
		for (SWTBotShell shell : shells) {
			if (shell.isOpen() && !isEclipseShell(shell)) {
				shell.close();
			}
		}
	}

	@SuppressWarnings("boxing")
	protected static boolean isEclipseShell(final SWTBotShell shell) {
		return UIThreadRunnable.syncExec(new BoolResult() {
			@Override
			public Boolean run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getShell() == shell.widget;
			}
		});
	}
