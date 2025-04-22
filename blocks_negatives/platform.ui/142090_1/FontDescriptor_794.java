    /**
     * Returns the set of FontData associated with this font. Modifying the elements
     * in the returned array has no effect on the original FontDescriptor.
     *
     * @return the set of FontData associated with this font
     * @since 3.3
     */
    public FontData[] getFontData() {
    	Font tempFont = createFont(Display.getCurrent());
    	FontData[] result = tempFont.getFontData();
    	destroyFont(tempFont);
    	return result;
    }
