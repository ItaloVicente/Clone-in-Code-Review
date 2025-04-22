		Iterator<FontRecord> iterator = stringToFontRecord.values().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			((FontRecord) next).dispose();
		}

		disposeFonts(staleFonts.iterator());
		stringToFontRecord.clear();
		staleFonts.clear();

		displayDisposeHooked = false;
	}

	private void disposeFonts(Iterator<Font> iterator) {
		while (iterator.hasNext()) {
			Object next = iterator.next();
			((Font) next).dispose();
		}
	}

	private void hookDisplayDispose(Display display) {
		displayDisposeHooked = true;
		display.disposeExec(displayRunnable);
	}

	private boolean isFixedFont(FontData[] fixedFonts, FontData fd) {
		int height = fd.getHeight();
		String name = fd.getName();
		for (FontData fixed : fixedFonts) {
			if (fixed.getHeight() == height && fixed.getName().equals(name)) {
