            FontData[] italicData = getModifiedFontData(SWT.ITALIC);
            italicFont = new Font(Display.getCurrent(), italicData);
            return italicFont;
        }

        /**
         * Add any fonts that were allocated for this record to the
         * stale fonts. Anything that matches the default font will
         * be skipped.
         * @param defaultFont The system default.
         */
        void addAllocatedFontsToStale(Font defaultFont) {
            if (defaultFont != baseFont && baseFont != null) {
