		ContextMenuHelper.clickContextMenu(tree, "Team", "Synchronize...");
	}

	private void handleConfirmOpenPerspective() {
		if (!changePerspective) {
			changePerspective = true;
			bot.waitUntil(shellIsActive("Confirm Open Perspective"), 15000);
			bot.checkBox("Remember my decision").click();
			bot.button("No").click();
		}
