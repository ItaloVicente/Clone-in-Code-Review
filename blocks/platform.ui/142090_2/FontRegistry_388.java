			FontData[] italicData = getModifiedFontData(SWT.ITALIC);
			italicFont = new Font(Display.getCurrent(), italicData);
			return italicFont;
		}

		void addAllocatedFontsToStale(Font defaultFont) {
			if (defaultFont != baseFont && baseFont != null) {
