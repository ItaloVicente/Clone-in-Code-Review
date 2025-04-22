			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.WINDOW__HEIGHT, oldHeight, height,
					!oldHeightESet));
	}

	@Override
	public void unsetHeight() {
		int oldHeight = height;
		boolean oldHeightESet = heightESet;
		height = HEIGHT_EDEFAULT;
		heightESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BasicPackageImpl.WINDOW__HEIGHT, oldHeight,
					HEIGHT_EDEFAULT, oldHeightESet));
	}

	@Override
	public boolean isSetHeight() {
		return heightESet;
