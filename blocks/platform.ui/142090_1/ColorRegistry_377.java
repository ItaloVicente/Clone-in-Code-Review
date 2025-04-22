	}

	private Color createColor(RGB rgb) {
		if (this.display == null) {
			Display display = Display.getCurrent();
			if (display == null) {
				throw new IllegalStateException();
			}
			this.display = display;
			if (cleanOnDisplayDisposal) {
				hookDisplayDispose();
			}
		}
		return new Color(display, rgb);
	}

	private void disposeColors(Iterator<Color> iterator) {
		while (iterator.hasNext()) {
			Object next = iterator.next();
			((Color) next).dispose();
		}
	}

	public Color get(String symbolicName) {

		Assert.isNotNull(symbolicName);
		Object result = stringToColor.get(symbolicName);
		if (result != null) {
