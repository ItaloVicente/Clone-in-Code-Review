		super.contributeToMenu(menuManager);

		MenuManager readmeMenu = new MenuManager(MessageUtil.getString("Readme_Menu")); //$NON-NLS-1$
		menuManager.insertAfter("additions", readmeMenu); //$NON-NLS-1$
		readmeMenu.add(action1);
		readmeMenu.add(action2);
		readmeMenu.add(action3);
	}

	@Override
