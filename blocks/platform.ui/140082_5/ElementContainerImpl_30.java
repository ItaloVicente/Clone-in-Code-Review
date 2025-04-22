	private void setSelectedElementGen(T newSelectedElement) {
		T oldSelectedElement = selectedElement;
		selectedElement = newSelectedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UiPackageImpl.ELEMENT_CONTAINER__SELECTED_ELEMENT, oldSelectedElement, selectedElement));
	}

	@Override
