	public String toString() {
		String result = toStringGen();
		if (elementId != null) {
			result = elementId + "=" + result.replace(" (elementId: " + elementId + ", ", " (");
		}
		return result.toString();
	}

