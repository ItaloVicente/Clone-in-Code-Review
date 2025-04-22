			eNotify(new ENotificationImpl(this, Notification.SET, AdvancedPackageImpl.PERSPECTIVE__TOOLTIP, oldTooltip,
					tooltip));
	}

	@Override
	public String getLocalizedLabel() {
		return LocalizationHelper.getLocalizedFeature(UiPackageImpl.Literals.UI_LABEL__LABEL, this);
