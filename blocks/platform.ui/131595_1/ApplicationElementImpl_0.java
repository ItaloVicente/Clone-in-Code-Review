	public boolean isPersistable() {
		return persistable;
	}

	public void setPersistable(boolean newPersistable) {
		boolean oldPersistable = persistable;
		persistable = newPersistable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApplicationPackageImpl.APPLICATION_ELEMENT__PERSISTABLE, oldPersistable, persistable));
	}

