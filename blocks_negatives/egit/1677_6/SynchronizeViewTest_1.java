		handleConfirmOpenPerspective();

		bot.waitUntil(shellIsActive("Git Resource Synchronization"), 15000);
		SWTBotShell gitResSyncShell = bot.shell("Git Resource Synchronization");
		bot.waitUntil(shellCloses(gitResSyncShell), 300000);

