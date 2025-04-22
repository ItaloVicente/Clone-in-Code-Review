			if (ActionFactory.NEW.getId().equals(manager.getId())) {
				initializeNewWizardsMenu(menuEntry);
				wizards = menuEntry;
			} else if (SHORTCUT_CONTRIBUTION_ITEM_ID_OPEN_PERSPECTIVE.equals(manager.getId())) {
				initializePerspectivesMenu(menuEntry);
				perspectives = menuEntry;
			} else if (SHORTCUT_CONTRIBUTION_ITEM_ID_SHOW_VIEW.equals(manager.getId())) {
				initializeViewsMenu(menuEntry);
				views = menuEntry;
			} else {
				createMenuEntries((MMenu) menuItem, menuEntry);
			}
