	public void updateLocalization() {
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(
					this, Notification.SET, CommandsPackageImpl.CATEGORY__LOCALIZED_NAME, null, getLocalizedName()));
			eNotify(new ENotificationImpl(
					this, Notification.SET, CommandsPackageImpl.CATEGORY__LOCALIZED_DESCRIPTION, null, getLocalizedDescription()));
		}
	}

