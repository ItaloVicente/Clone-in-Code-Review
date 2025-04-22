		return stringToRGB.containsKey(colorKey);
	}

	private void hookDisplayDispose() {
		display.disposeExec(displayRunnable);
	}

	public void put(String symbolicName, RGB colorData) {
		put(symbolicName, colorData, true);
	}

	private void put(String symbolicName, RGB colorData, boolean update) {

		Assert.isNotNull(symbolicName);
		Assert.isNotNull(colorData);

		RGB existing = stringToRGB.get(symbolicName);
		if (colorData.equals(existing)) {
