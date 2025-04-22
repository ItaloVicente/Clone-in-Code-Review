		boolean oldXESet = xESet;
		xESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.WINDOW__X, oldX, x, !oldXESet));
	}

	@Override
	public void unsetX() {
		int oldX = x;
		boolean oldXESet = xESet;
		x = X_EDEFAULT;
		xESet = false;
