		return LocalizationHelper.getLocalizedAccessibilityPhrase(this);
	}

	public void updateLocalization() {
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(
					this, Notification.SET, UiPackageImpl.UI_ELEMENT__LOCALIZED_ACCESSIBILITY_PHRASE, null, getLocalizedAccessibilityPhrase()));
		}
