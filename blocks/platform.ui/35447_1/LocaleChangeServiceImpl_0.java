			if (element instanceof MWindow) {
				MWindow window = (MWindow) element;
				MMenu mainMenu = window.getMainMenu();
				if (mainMenu != null) {
					mainMenu.updateLocalization();
					updateLocalization(mainMenu.getChildren());
				}
				updateLocalization(window.getSharedElements());
