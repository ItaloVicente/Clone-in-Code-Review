			Object expectedValue) {
		ListElement le = (ListElement) receiver;
		if (property.equals(ATTR_NAME)) {
			return expectedValue.equals(le.getName());
		}
		return false;
	}
