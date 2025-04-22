		boolean oldYESet = yESet;
		yESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.WINDOW__Y, oldY, y, !oldYESet));
	}

	@Override
	public void unsetY() {
		int oldY = y;
		boolean oldYESet = yESet;
		y = Y_EDEFAULT;
		yESet = false;
