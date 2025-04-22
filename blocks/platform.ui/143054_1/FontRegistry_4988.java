	private class FontRecord {

		Font baseFont;

		Font boldFont;

		Font italicFont;

		FontData[] baseData;

		FontRecord(Font plainFont, FontData[] data) {
			baseFont = plainFont;
			baseData = data;
		}

		void dispose() {
			baseFont.dispose();
			if (boldFont != null) {
