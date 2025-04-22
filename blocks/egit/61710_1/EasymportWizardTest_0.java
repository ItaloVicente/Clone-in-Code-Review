		final SWTBotMenu fileMenu = waitForMenu(new MenuFinder() {
			@Override
			public SWTBotMenu menu(String label)
					throws WidgetNotFoundException {
				return bot.menu(label);
			}
		}, "File", 30);
		waitForMenu(new MenuFinder() {
			@Override
			public SWTBotMenu menu(String label)
					throws WidgetNotFoundException {
				return fileMenu.menu(label);
			}
		}, "Import...", 30).click();
