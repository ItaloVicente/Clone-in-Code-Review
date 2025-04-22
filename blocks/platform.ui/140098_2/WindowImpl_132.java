			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					BasicPackageImpl.WINDOW__MAIN_MENU, oldMainMenu, newMainMenu);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
