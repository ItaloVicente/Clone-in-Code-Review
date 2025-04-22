	public void updateLocalization() {
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(
					this, Notification.SET, BasicPackageImpl.WINDOW__LOCALIZED_LABEL, null, getLocalizedLabel()));
			eNotify(new ENotificationImpl(
					this, Notification.SET, BasicPackageImpl.WINDOW__LOCALIZED_TOOLTIP, null, getLocalizedTooltip()));
		}

	}

