
				if (enableMainMenu) {
					Menu mainMenu = (Menu) model.getMainMenu().getWidget();
					mainMenu.setEnabled(true);
				}
				
				for (Control ctrl : toEnable) {
					if (!ctrl.isDisposed())
						ctrl.setEnabled(true);
				}
