			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					BasicPackageImpl.PART__TOOLBAR, oldToolbar, newToolbar);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
