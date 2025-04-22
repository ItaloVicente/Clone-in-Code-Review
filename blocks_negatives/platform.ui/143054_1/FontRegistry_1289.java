            FontData[] boldData = getModifiedFontData(SWT.BOLD);
            boldFont = new Font(Display.getCurrent(), boldData);
            return boldFont;
        }

        /**
         * Get a version of the base font data with the specified
         * style.
         * @param style the new style
         * @return the font data with the style {@link FontData#FontData(String, int, int)}
         * @see SWT#ITALIC
         * @see SWT#NORMAL
         * @see SWT#BOLD
         * @todo Generated comment
         */
        private FontData[] getModifiedFontData(int style) {
            FontData[] styleData = new FontData[baseData.length];
            for (int i = 0; i < styleData.length; i++) {
                FontData base = baseData[i];
                styleData[i] = new FontData(base.getName(), base.getHeight(),
                        base.getStyle() | style);
            }

            return styleData;
        }

        /**
         * Return the italic Font. Create an italic version of the
         * base font to get it.
         * @return Font
         */
        public Font getItalicFont() {
            if (italicFont != null) {
