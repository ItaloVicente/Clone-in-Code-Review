	public void updateLocalization() {
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(
					this, Notification.SET, CommandsPackageImpl.COMMAND__LOCALIZED_COMMAND_NAME, null, getLocalizedCommandName()));
			eNotify(new ENotificationImpl(
					this, Notification.SET, CommandsPackageImpl.COMMAND__LOCALIZED_DESCRIPTION, null, getLocalizedDescription()));
		}
	}

