	private EventHandler windowWidgetHandler = event -> {
		if (event.getProperty(UIEvents.EventTags.ELEMENT) == model
				&& event.getProperty(UIEvents.EventTags.NEW_VALUE) == null) {
			manageChanges = false;
			canUpdateMenus = false;
			menuUpdater = null;

			MMenu menu = model.getMainMenu();
			if (menu != null) {
				engine.removeGui(menu);
				model.setMainMenu(null);
