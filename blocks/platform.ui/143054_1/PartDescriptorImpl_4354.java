			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					BasicPackageImpl.PART_DESCRIPTOR__TOOLBAR, oldToolbar, newToolbar);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
