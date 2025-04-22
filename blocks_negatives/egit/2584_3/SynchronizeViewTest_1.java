		launchSynchronization(SynchronizeWithAction_tagsName, "v0.1",
				SynchronizeWithAction_tagsName, "v0.2", false);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();

		waitUntilTreeHasNodeWithText(syncViewTree, "test commit");
