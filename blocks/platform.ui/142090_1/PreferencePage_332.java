	}

	public void setValid(boolean b) {
		boolean oldValue = isValid;
		isValid = b;
		if (oldValue != isValid) {
			if (getContainer() != null) {
