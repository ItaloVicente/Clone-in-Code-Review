			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN, oldVisibleWhen, newVisibleWhen);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
