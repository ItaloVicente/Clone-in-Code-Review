			eNotify(new ENotificationImpl(this, Notification.UNSET, BasicPackageImpl.WINDOW__X, oldX, X_EDEFAULT,
					oldXESet));
	}

	@Override
	public boolean isSetX() {
		return xESet;
