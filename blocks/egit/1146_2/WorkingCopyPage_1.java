		try {
			SWTBotShell shell = bot.shell("Cloning from " + cloneUrl);

			bot.waitUntil(shellCloses(shell), 120000);
		} catch (WidgetNotFoundException e1) {
			for (int i = 0; i < 10; i++) {
				if (Activator.getDefault().getRepositoryUtil()
						.getConfiguredRepositories().contains(targetDir))
					return;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			fail("The Repository was not created");
		}
