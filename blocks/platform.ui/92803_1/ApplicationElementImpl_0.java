	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String newExplanation) {
		String oldExplanation = explanation;
		explanation = newExplanation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ApplicationPackageImpl.APPLICATION_ELEMENT__EXPLANATION, oldExplanation, explanation));
	}

