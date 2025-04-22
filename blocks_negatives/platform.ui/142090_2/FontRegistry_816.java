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

    /**
     * Dispose of all of the fonts in this iterator.
     * @param iterator over Collection of Font
     */
    private void disposeFonts(Iterator<Font> iterator) {
        while (iterator.hasNext()) {
            Object next = iterator.next();
            ((Font) next).dispose();
        }
    }

    /**
     * Hook a dispose listener on the SWT display.
     */
    private void hookDisplayDispose(Display display) {
    	displayDisposeHooked = true;
    	display.disposeExec(displayRunnable);
    }

    /**
     * Checks whether the given font is in the list of fixed fonts.
     */
    private boolean isFixedFont(FontData[] fixedFonts, FontData fd) {
        int height = fd.getHeight();
        String name = fd.getName();
        for (FontData fixed : fixedFonts) {
            if (fixed.getHeight() == height && fixed.getName().equals(name)) {
