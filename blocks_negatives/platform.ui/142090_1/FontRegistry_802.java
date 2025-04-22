    /**
     * FontRecord is a private helper class that holds onto a font
     * and can be used to generate its bold and italic version.
     */
    private class FontRecord {

        Font baseFont;

        Font boldFont;

        Font italicFont;

        FontData[] baseData;

        /**
         * Create a new instance of the receiver based on the
         * plain font and the data for it.
         * @param plainFont The base looked up font.
         * @param data The data used to look it up.
         */
        FontRecord(Font plainFont, FontData[] data) {
            baseFont = plainFont;
            baseData = data;
        }

        /**
         * Dispose any of the fonts created for this record.
         */
        void dispose() {
            baseFont.dispose();
            if (boldFont != null) {
