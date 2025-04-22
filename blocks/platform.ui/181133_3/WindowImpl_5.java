			eNotify(new ENotificationImpl(this, Notification.UNSET, BasicPackageImpl.WINDOW__WIDTH, oldWidth,
					WIDTH_EDEFAULT, oldWidthESet));
	}

	@Override
	public boolean isSetWidth() {
		return widthESet;
