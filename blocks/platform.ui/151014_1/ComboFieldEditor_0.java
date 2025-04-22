	protected void valueChanged(String oldValue, String newValue) {
		if (oldValue != null && !oldValue.equals(newValue) || newValue != null) {
			fireValueChanged(VALUE, oldValue, newValue);
		}
	}

