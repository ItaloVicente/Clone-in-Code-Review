			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MenuPackageImpl.TOOL_ITEM__MENU, oldMenu, newMenu);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
