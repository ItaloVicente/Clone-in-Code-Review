	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date newLastModified) {
		Date oldLastModified = lastModified;
		lastModified = newLastModified;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.PART__LAST_MODIFIED, oldLastModified, lastModified));
	}

