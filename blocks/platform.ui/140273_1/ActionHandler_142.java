	private void detachListener() {
		this.action.removePropertyChangeListener(propertyChangeListener);
		propertyChangeListener = null;
		attributeValuesByName = null;
	}

