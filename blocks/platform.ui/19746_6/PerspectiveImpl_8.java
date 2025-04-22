	public void updateLocalization() {
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(
					this, Notification.SET, AdvancedPackageImpl.PERSPECTIVE__LOCALIZED_LABEL, null, getLocalizedLabel()));
			eNotify(new ENotificationImpl(
					this, Notification.SET, AdvancedPackageImpl.PERSPECTIVE__LOCALIZED_TOOLTIP, null, getLocalizedTooltip()));
		}
	}

