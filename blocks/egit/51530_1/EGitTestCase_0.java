	@Before
	public void activateShell() {
		SWTBotShell[] shells = bot.shells();
		for (SWTBotShell shell : shells) {
			if (Eclipse.isEclipseShell(shell)) {
				shell.activate();
				return;
			}
		}
		fail("No active Eclipse shell found!");
	}

