
		handleConfirmOpenPerspective();

		bot.waitUntil(shellIsActive("Git Resource Synchronization"), 15000);
		SWTBotShell shell = bot.shell("Git Resource Synchronization");
		bot.waitUntil(shellCloses(shell), 300000);
