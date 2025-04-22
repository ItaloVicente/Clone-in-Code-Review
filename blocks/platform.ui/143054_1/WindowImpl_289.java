			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.WINDOW__TOOLTIP, oldTooltip,
					tooltip));
	}

	@Override
	public String getLocalizedLabel() {
		return LocalizationHelper.getLocalizedFeature(UiPackageImpl.Literals.UI_LABEL__LABEL, this);
	}

	@Override
	public String getLocalizedTooltip() {
		return LocalizationHelper.getLocalizedFeature(UiPackageImpl.Literals.UI_LABEL__TOOLTIP, this);
