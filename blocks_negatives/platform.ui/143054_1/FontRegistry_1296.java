        FontData[] datas = new FontData[1];
        datas[0] = bestData;
        return datas;
    }

    /**
     * Removes from the list all fonts that do not exist in this system.
     * If none are valid, return the first irregardless.  If the list is
     * empty return <code>null</code>.
     *
     * @param fonts the fonts to check
     * @param display the display to check against
     * @return the list of fonts that have been found on this system
     * @since 3.1
     */
    public FontData [] filterData(FontData [] fonts, Display display) {
    	ArrayList<FontData> good = new ArrayList<>(fonts.length);
    	for (FontData fd : fonts) {
            if (fd == null) {
