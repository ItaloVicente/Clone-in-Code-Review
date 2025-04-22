		fireValueChanged(property, oldValue ? Boolean.TRUE : Boolean.FALSE, newValue ? Boolean.TRUE : Boolean.FALSE);
	}

	protected void fireValueChanged(String property, Object oldValue,
			Object newValue) {
		if (propertyChangeListener == null) {
