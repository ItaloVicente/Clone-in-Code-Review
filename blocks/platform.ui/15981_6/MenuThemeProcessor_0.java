		MCommand switchThemeCommand = findCommand(app);

		if (themes.size() <= 0 || switchThemeCommand == null) {
			return;
		}

		themesMenu = service.createModelElement(MMenu.class);
