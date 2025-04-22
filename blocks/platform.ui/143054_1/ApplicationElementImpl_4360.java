			eNotify(new ENotificationImpl(this, Notification.SET,
					ApplicationPackageImpl.APPLICATION_ELEMENT__CONTRIBUTOR_URI, oldContributorURI, contributorURI));
	}

	@Override
	public void setContributorURI(String newContributorURI) {
		setContributorURIGen(newContributorURI == null ? null : newContributorURI.intern());
