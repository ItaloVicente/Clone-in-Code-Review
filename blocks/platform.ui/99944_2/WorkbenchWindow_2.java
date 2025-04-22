			if (window.getMainMenu() != null) {
				MMenu mainMenu = window.getMainMenu();
				final MenuManagerRenderer renderer = (MenuManagerRenderer) rendererFactory.getRenderer(mainMenu, null);
				cleanupMenuManagerRec(renderer, mainMenu);
			}

