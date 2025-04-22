
			if (themeChanged || colorsAndFontsThemeChanged) {
				if (notificationPopUp == null) {
					notificationPopUp = new NotificationPopUp(getShell().getDisplay());
					notificationPopUp.open();
				}
			}
