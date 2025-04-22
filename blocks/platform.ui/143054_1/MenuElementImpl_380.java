	public void setMnemonics(String newMnemonics) {
		String oldMnemonics = mnemonics;
		mnemonics = newMnemonics;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackageImpl.MENU_ELEMENT__MNEMONICS, oldMnemonics,
					mnemonics));
