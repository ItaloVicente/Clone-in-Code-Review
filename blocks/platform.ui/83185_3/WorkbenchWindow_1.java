					MMenu mainMenu = model.getMainMenu();
					if (mainMenu != null) {
						Menu menuWidget = (Menu) mainMenu.getWidget();
						if (menuWidget != null && !menuWidget.isDisposed() && !menuWidget.isEnabled())
							menuWidget.setEnabled(true);
					}
