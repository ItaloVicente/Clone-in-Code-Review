	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getInputURI() {
		return inputURI;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInputURI(String newInputURI) {
		String oldInputURI = inputURI;
		inputURI = newInputURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__INPUT_URI, oldInputURI, inputURI));
	}
