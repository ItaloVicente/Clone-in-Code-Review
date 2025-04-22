		PushConfiguredRemoteAction action = new PushConfiguredRemoteAction(
				repository, "origin");

		action.run(bot.activeShell().widget, false);

		destinationString = clonedRepositoryFile2.getParentFile().getName()
				+ " - " + "origin";
