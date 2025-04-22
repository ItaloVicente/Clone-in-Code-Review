	@Override
	public void updateLocalization() {
		if (eNotificationRequired()) {
			super.updateLocalization();
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackageImpl.MENU_ELEMENT__LOCALIZED_LABEL, null,
					getLocalizedLabel()));
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackageImpl.MENU_ELEMENT__LOCALIZED_TOOLTIP, null,
					getLocalizedTooltip()));
		}
